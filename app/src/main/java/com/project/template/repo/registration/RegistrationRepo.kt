package com.project.template.repo.registration

import com.project.template.model.RegistrationRequestModel

class RegistrationRepo(var registrationRDS: RegistrationRds) {

    fun registrationApiCallRepo(registrationRequestModel: RegistrationRequestModel) =
        registrationRDS.registrationApiCallRDS(registrationRequestModel)

}
