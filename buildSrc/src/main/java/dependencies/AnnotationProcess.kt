package dependencies

object AnnotationProcess {
    val hiltAnnotationProcess = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    val hiltViewModelAnnotationProcess = "androidx.hilt:hilt-compiler:${Versions.hilt_viewmodel}"
    val lifeCycleAnnotationProcess = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"
}