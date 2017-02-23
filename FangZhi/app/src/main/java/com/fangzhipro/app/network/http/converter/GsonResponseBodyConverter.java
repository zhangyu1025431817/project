/*
 * Copyright (C) 2015 Square, Inc.
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

package com.fangzhipro.app.network.http.converter;

import com.fangzhipro.app.network.http.api.ApiModel;
import com.fangzhipro.app.network.http.api.ErrorCode;
import com.fangzhipro.app.network.http.exception.DeviceException;
import com.fangzhipro.app.network.http.exception.TimeException;
import com.fangzhipro.app.network.http.exception.TokenInvalidException;
import com.fangzhipro.app.network.http.exception.UserInvalidException;
import com.fangzhipro.app.network.http.exception.UserPasswordException;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            ApiModel apiModel = (ApiModel) adapter.fromJson(value.charStream());
            if (ErrorCode.TOKEN_INVALID.equals(apiModel.error_code)) {
                throw new TokenInvalidException(apiModel.msg);
            } else if (ErrorCode.DEVICE_INVALID.equals(apiModel.error_code)) {
                throw new DeviceException(apiModel.msg);
            } else if (ErrorCode.USER_PASSWORD_ERROR.equals(apiModel.error_code)) {
                throw new UserPasswordException(apiModel.msg);
            } else if (ErrorCode.USER_INVALID.equals(apiModel.error_code)) {
                throw new UserInvalidException(apiModel.msg);
            } else if (ErrorCode.TIME_INVALID.equals(apiModel.error_code)) {
                throw new TimeException(apiModel.msg);
            } else if (ErrorCode.SUCCEED.equals(apiModel.error_code)) {
                return value;
            }
        } finally {
            value.close();
        }
        return null;
    }
}
