package machine7y.mapdownloader.domain.base.usecase

import kotlinx.coroutines.flow.Flow

abstract class BaseParamsFlowUseCase<in Param, out Result> {

    suspend operator fun invoke(param: Param): Flow<Result> {
        return execute(param)
    }

    protected abstract suspend fun execute(params: Param): Flow<Result>
}
