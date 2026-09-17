package de.visualdigits.graffitomat.presentation.model

import de.visualdigits.common.domain.model.ui.KeyValue

sealed interface GraffitomatAction {

    data class OnCreateRequestValueChanged(
        val keyValue: KeyValue,
    ) : GraffitomatAction

    class OnCreateRequestCancelClick : GraffitomatAction

    class OnCreateRequestOkClick : GraffitomatAction
}
