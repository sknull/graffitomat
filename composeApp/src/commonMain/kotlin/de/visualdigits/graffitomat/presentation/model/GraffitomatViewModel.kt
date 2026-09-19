package de.visualdigits.graffitomat.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.presentation.components.applyAppLanguage
import de.visualdigits.common.presentation.model.ScrollIntent
import de.visualdigits.graffitomat.domain.model.createrequest.CreateRequestForm
import de.visualdigits.graffitomat.domain.model.createrequest.RK
import de.visualdigits.graffitomat.domain.model.type.Language
import de.visualdigits.graffitomat.domain.repository.BackendRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class GraffitomatViewModel(
    private val backendRepository: BackendRepository
) : ViewModel() {

    val now = KmpOffsetDateTime.fromString("2026-08-15T00:00:00.000000000+02:00") // just for the demo

    val scrollPosition: MutableMap<String, Triple<Int, Int?, ScrollIntent>> = mutableMapOf()

    @OptIn(ExperimentalUuidApi::class)
    private val _state = MutableStateFlow(GraffitomatState())
    val state = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        Logger.i("Application started")
        applyAppLanguage(Language.DE.localeCode)
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onAction(action: GraffitomatAction) {
        when (action) {
            is GraffitomatAction.OnCreateRequestValueChanged -> {
                _state.update {
                    it.copy(
                        editedRequest = it.editedRequest.copy(
                            key = action.keyValue.descriptor.key as RK,
                            value = action.keyValue.value
                        )
                    )
                }
            }
            is GraffitomatAction.OnCreateRequestCancelClick -> {
                _state.update {
                    it.copy(
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                        selectedTabIndex = 0,
                        uiMessage = null,
                        uiMessageSeverity = null
                    )
                }
            }
            is GraffitomatAction.OnCreateRequestOkClick -> {
                createGraffitoRequest(state.value.editedRequest)
            }
            is GraffitomatAction.OnLanguageClicked -> {
                applyAppLanguage(action.language.localeCode)
                _state.update {
                    it.copy(
                        currentLanguage = action.language,
                        forceUpdate = Uuid.random()
                    )
                }
            }
            else -> {}
        }
    }

    private fun createGraffitoRequest(request: CreateRequestForm?) = viewModelScope.launch {
        checkNotNull(request) { "No request to execute" }
        backendRepository.createGraffitoRequest(request)
    }
}
