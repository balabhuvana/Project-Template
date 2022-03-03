package com.project.template.repo.registration

import com.project.template.model.RegistrationRequestModel

class RegistrationRepoViaFlow(var registrationRDSViaFlow: RegistrationRdsViaFlow) {

    fun registrationApiCallRepo(registrationRequestModel: RegistrationRequestModel) =
        registrationRDSViaFlow.registrationApiCallRDS(registrationRequestModel)

}
