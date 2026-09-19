package de.visualdigits.graffitomat.presentation.model

import androidx.compose.runtime.Stable
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.graffitomat.domain.model.createrequest.CreateRequestForm
import de.visualdigits.graffitomat.domain.model.type.Language
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Stable
@OptIn(ExperimentalUuidApi::class)
data class GraffitomatState(

    val previousSelectedTabIndexes: List<Int> = listOf(),
    val selectedTabIndex: Int = 0,
    val tabLabels: List<Pair<String, UiText>> = listOf(),
    val tabLabelKeys: List<String> = listOf(),

    val editedRequest: CreateRequestForm = CreateRequestForm(),

    val collapsibleState: Map<String, Boolean> = mapOf(),

    val uiMessage: UiText? = null,
    val uiMessageSeverity: Severity? = null,

    val forceUpdate: Uuid? = null,

    val currentLanguage: Language = Language.DE
) {
    fun tabIndex(key: String): Int {
        return tabLabels
            .find { tl -> tl.first == key }
            ?.let { pair -> tabLabels.indexOf(pair) }
            ?: 0
    }
}
