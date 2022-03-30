package com.project.template.repo.registration

import com.project.template.model.RegistrationRequestModel
import com.project.template.network.ApiWebService
import kotlinx.coroutines.flow.flow

class RegistrationRds(var apiWebService: ApiWebService?) {

    fun registrationApiCallRDS(registrationRequestModel: RegistrationRequestModel) =
        flow {
            emit(apiWebService?.registrationApiCall(registrationRequestModel))
        }
}
