package de.visualdigits.graffitomat.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.presentation.components.applyAppLanguage
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.common.presentation.model.ScrollIntent
import de.visualdigits.graffitomat.domain.model.type.Language
import de.visualdigits.graffitomat.domain.repository.BackendRepository
import de.visualdigits.graffitomat.domain.model.createrequest.CreateRequestForm
import de.visualdigits.graffitomat.domain.model.createrequest.RK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class GraffitomatViewModel(
    private val backendRepository: BackendRepository
) : ViewModel() {

    val now = KmpOffsetDateTime.fromString("2026-08-15T00:00:00.000000000+02:00") // just for the demo

    val scrollPosition: MutableMap<String, Triple<Int, Int?, ScrollIntent>> = mutableMapOf()

    private val _state = MutableStateFlow(GraffitomatState())
    val state = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _editedRequest = MutableStateFlow<CreateRequestForm>(CreateRequestForm())
    val editedRequest = _editedRequest.asStateFlow()

    init {
        Logger.i("Application started")
        applyAppLanguage(Language.EN.localeCode)
    }

    fun onCommonAction(action: CommonAction) {
        when (action) {
            is CommonAction.OnScrollPositionChange -> {
                action.id?.also { id ->
                    scrollPosition[id] = Triple(action.position, action.offset, action.scrollIntent)
                }
            }
        }
    }

    fun onAction(action: GraffitomatAction) {
        when (action) {
            is GraffitomatAction.OnCreateRequestValueChanged -> {
                _editedRequest.update { current ->
                    current.copy(
                        key = action.keyValue.descriptor.key as RK,
                        value = action.keyValue.value
                    )
                }
            }
            is GraffitomatAction.OnCreateRequestCancelClick -> {
                _state.update {
                    it.copy(
                        isEditingSettings = false,
                        previousSelectedTabIndexes = it.previousSelectedTabIndexes + it.selectedTabIndex,
                        selectedTabIndex = 0,
                        uiMessage = null,
                        uiMessageSeverity = null
                    )
                }
            }
            is GraffitomatAction.OnCreateRequestOkClick -> {
                createGraffitoRequest(_editedRequest.value)
            }
            else -> {}
        }
    }

    private fun createGraffitoRequest(request: CreateRequestForm?) = viewModelScope.launch {
        checkNotNull(request) { "No request to execute" }
        backendRepository.createGraffitoRequest(request)
    }
}
