package com.project.template.repo.registration

import com.project.template.model.RegistrationRequestModel

class RegistrationRepoViaFlow(var registrationRDSViaFlow: RegistrationRDSViaFlow) {

    fun registrationApiCallRepo(registrationRequestModel: RegistrationRequestModel) =
        registrationRDSViaFlow.registrationApiCallRDS(registrationRequestModel)

}
