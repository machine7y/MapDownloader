package machine7y.mapdownloader.domain.base.usecase

import kotlinx.coroutines.flow.Flow

abstract class BaseNoParamsFlowUseCase<out Result> {

    suspend operator fun invoke(): Flow<Result> {
        return execute()
    }

    protected abstract suspend fun execute(): Flow<Result>
}
