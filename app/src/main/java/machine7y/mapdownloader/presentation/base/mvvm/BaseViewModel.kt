package machine7y.mapdownloader.presentation.base.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class BaseViewModel<State : BaseState, InternalState : BaseInternalState, Event : BaseEvent, Label : BaseLabel>(
    initialState: State,
    initialInternalState: InternalState,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()
    val state: State get() = _stateFlow.value

    private val _labelFlow = MutableSharedFlow<Label>()
    val labelFlow = _labelFlow.asSharedFlow()

    private val _internalStateFlow = MutableStateFlow(initialInternalState)
    protected val internalStateFlow = _internalStateFlow.asStateFlow()
    val internalState: InternalState get() = _internalStateFlow.value

    protected fun updateUiState(action: State.() -> State) {
        _stateFlow.update { action(_stateFlow.value) }
    }

    protected fun updateInternalState(action: InternalState.() -> InternalState) {
        _internalStateFlow.update { action(_internalStateFlow.value) }
    }

    protected suspend fun publishLabel(label: Label) {
        _labelFlow.emit(label)
    }

    protected fun launch(action: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(block = action)
    }

    open fun onEvent(event: Event) {
    }
}
