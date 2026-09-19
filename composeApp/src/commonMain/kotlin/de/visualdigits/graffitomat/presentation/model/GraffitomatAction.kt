package de.visualdigits.graffitomat.presentation.model

import de.visualdigits.common.domain.model.ui.KeyValue
import de.visualdigits.graffitomat.domain.model.type.Language

sealed interface GraffitomatAction {

    data class OnCreateRequestValueChanged(
        val keyValue: KeyValue,
    ) : GraffitomatAction

    class OnCreateRequestCancelClick : GraffitomatAction

    class OnCreateRequestOkClick : GraffitomatAction

    data class OnLanguageClicked(
        val language: Language
    ) : GraffitomatAction
}
