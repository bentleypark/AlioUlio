/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alio.ulio.buildsrc

object Versions {
    const val ktlint = "0.42.1"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.3"

    object Accompanist {
        const val version = "0.21.0-beta"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val systemuicontroller =
            "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val flowlayouts = "com.google.accompanist:accompanist-flowlayout:$version"
    }

    object Kotlin {
        private const val version = "1.6.0"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.5.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Hilt {
        private const val version = "2.40.1"
        const val hiltAndroid = "com.google.dagger:hilt-android:$version"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.2"

        object Compose {
            const val snapshot = ""
            const val version = "1.1.0-rc01"

            const val foundation = "androidx.compose.foundation:foundation:${version}"
            const val layout = "androidx.compose.foundation:foundation-layout:${version}"
            const val ui = "androidx.compose.ui:ui:${version}"
            const val uiUtil = "androidx.compose.ui:ui-util:${version}"
            const val runtime = "androidx.compose.runtime:runtime:${version}"
            const val material = "androidx.compose.material:material:${version}"
            const val animation = "androidx.compose.animation:animation:${version}"
            const val tooling = "androidx.compose.ui:ui-tooling:${version}"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.1"
            const val activityCompat = "androidx.appcompat:appcompat:1.4.0"
        }

        object Lifecycle {
            private const val version ="2.4.0"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            const val lifeCycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val saveState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
        }

        object Material {
            const val material = "com.google.android.material:material:1.4.0"
        }

        object Navigation {
            private const val version = "2.4.0-beta02"
            const val navigationCompose = "androidx.navigation:navigation-compose:$version"
            const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object ConstraintLayout {
            const val constraintLayoutCompose =
                "androidx.constraintlayout:constraintlayout-compose:1.0.0-rc01"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }
    }

    object JUnit {
        private const val version = "4.13"
        const val junit = "junit:junit:$version"
    }

    object DesugarJdk {
        const val desuagrJdk = "com.android.tools:desugar_jdk_libs:1.1.5"
    }

    object OkHttp {
        private const val version = "4.9.3"
        const val core = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gson = "com.google.code.gson:gson:2.8.7"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:1.3.2"
    }

    object SystemUiController {
        private const val version = "0.21.4-beta"
        const val systemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val permissions = "com.google.accompanist:accompanist-permissions:$version"
    }

    object CustomView {
        const val calendarView = "com.github.kizitonwose:CalendarView:1.0.4"
        const val expandableLayout ="net.cachapa.expandablelayout:expandablelayout:2.9.2"
        const val lottie = "com.airbnb.android:lottie:4.1.0"
    }

    const val timber = "com.jakewharton.timber:timber:4.7.1"
}
