package de.visualdigits.graffitomat.presentation.model

import androidx.compose.runtime.Stable
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.ui.UiText

@Stable
data class GraffitomatState(

    val previousSelectedTabIndexes: List<Int> = listOf(),
    val selectedTabIndex: Int = 0,
    val tabLabels: List<Pair<String, UiText>> = listOf(),
    val tabLabelKeys: List<String> = listOf(),

    val isEditingSettings: Boolean = false,

    val collapsibleState: Map<String, Boolean> = mapOf(),

    val uiMessage: UiText? = null,
    val uiMessageSeverity: Severity? = null,
) {
    fun tabIndex(key: String): Int {
        return tabLabels
            .find { tl -> tl.first == key }
            ?.let { pair -> tabLabels.indexOf(pair) }
            ?: 0
    }
}
