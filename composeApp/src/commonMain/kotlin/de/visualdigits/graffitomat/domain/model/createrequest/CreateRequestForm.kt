package de.visualdigits.graffitomat.domain.model.createrequest

import de.visualdigits.common.domain.model.configuration.AbstractConfiguration
import de.visualdigits.common.domain.model.configuration.StringFieldDescriptor
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_text

class CreateRequestForm(
    values: Map<RK, Any?> = mapOf()
): AbstractConfiguration<CreateRequestForm, RK>(values, DESCRIPTORS) {

    companion object {
        val DESCRIPTORS = listOf(
            StringFieldDescriptor(
                key = RK.text,
                label = UiText.StringResourceId(Res.string.label_text)
            ),
        )
    }

    override fun createInstance(newValues: Map<RK, Any?>): CreateRequestForm {
        return CreateRequestForm(newValues)
    }
}
