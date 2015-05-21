/*
 * Copyright 2014 CyberVision, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kaaproject.kaa.server.admin.services.util;

import org.kaaproject.kaa.server.admin.shared.services.KaaAdminServiceException;
import org.kaaproject.kaa.server.admin.shared.services.ServiceErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static KaaAdminServiceException handleException(Exception exception) {
        return handleException(exception, true);
    }

    public static KaaAdminServiceException handleException(Exception exception, boolean logException) {
        return handleExceptionWithCause(exception, null, null, logException);
    }

    public static KaaAdminServiceException handleExceptionWithCause(Exception exceptionWithCause, Class expectedCauseClass,
                                                                    String errorMessage, boolean logException) {
        if (logException) {
            logger.error("Unexpected exception catched!", exceptionWithCause);
        }

        Class causeClass = exceptionWithCause.getCause().getClass();
        if (exceptionWithCause instanceof KaaAdminServiceException) {
            return (KaaAdminServiceException) exceptionWithCause;
        } else if (causeClass != null && causeClass.equals(expectedCauseClass)) {
            return new KaaAdminServiceException(errorMessage, ServiceErrorCode.GENERAL_ERROR);
        } else {
            return new KaaAdminServiceException(exceptionWithCause.getMessage(), ServiceErrorCode.GENERAL_ERROR);
        }
    }
    
    public static <T> T checkNotNull(T reference) throws KaaAdminServiceException {
        if (reference == null) {
          throw new KaaAdminServiceException(ServiceErrorCode.ITEM_NOT_FOUND);
        }
        return reference;
    }

}
