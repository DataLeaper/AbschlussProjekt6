
package com.zacharee1.systemuituner.compose

import android.content.Context
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zacharee1.systemuituner.R
import dev.zwander.composeintroslider.IntroPage
import dev.zwander.composeintroslider.SimpleIntroPage
import dev.zwander.composeintroslider.SimpleStepsPage

private sealed class SelectedOS(val nameRes: Int, val iconRes: Int) {
    object Windows : SelectedOS(R.string.adb_windows, R.drawable.microsoft_windows) {
        override fun Context.makeSteps(permissions: Array<String>): Array<SimpleStepsPage.StepInfo> {
            val commands = permissions.map {
                SimpleStepsPage.StepInfo(
                    resources.getString(R.string.adb_command_template_windows, packageName, it),
                    true
                )
            }
            return arrayOf(
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_windows_1)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_windows_2)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_windows_3)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_windows_4)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_general_end_1)
                )
            ) + commands +
                    arrayOf(
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_2)
                        ),
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_3)
                        )
                    )
        }
    }
    object MacOS : SelectedOS(R.string.adb_mac, R.drawable.baseline_keyboard_command_key_24) {
        override fun Context.makeSteps(permissions: Array<String>): Array<SimpleStepsPage.StepInfo> {
            val commands = permissions.map {
                SimpleStepsPage.StepInfo(
                    resources.getString(R.string.adb_command_template_mac_linux, packageName, it),
                    true
                )
            }
            return arrayOf(
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_mac_1)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_mac_2)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_mac_3)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_mac_4)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_general_end_1)
                )
            ) + commands +
                    arrayOf(
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_2)
                        ),
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_3)
                        )
                    )
        }
    }
    object Fedora : SelectedOS(R.string.adb_linux_fedora, R.drawable.penguin) {
        override fun Context.makeSteps(permissions: Array<String>): Array<SimpleStepsPage.StepInfo> {
            val commands = permissions.map {
                SimpleStepsPage.StepInfo(
                    resources.getString(R.string.adb_command_template_linux_installed, packageName, it),
                    true
                )
            }
            return arrayOf(
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_fedora_1)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_fedora_2)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_general_end_1)
                )
            ) + commands +
                    arrayOf(
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_2)
                        ),
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_3)
                        )
                    )
        }
    }
    object Debian : SelectedOS(R.string.adb_linux_debian, R.drawable.penguin) {
        override fun Context.makeSteps(permissions: Array<String>): Array<SimpleStepsPage.StepInfo> {
            val commands = permissions.map {
                SimpleStepsPage.StepInfo(
                    resources.getString(R.string.adb_command_template_linux_installed, packageName, it),
                    true
                )
            }
            return arrayOf(
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_debian_1)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_debian_2)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_general_end_1)
                )
            ) + commands +
                    arrayOf(
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_2)
                        ),
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_3)
                        )
                    )
        }
    }
    object RHEL : SelectedOS(R.string.adb_linux_rhel, R.drawable.penguin) {
        override fun Context.makeSteps(permissions: Array<String>): Array<SimpleStepsPage.StepInfo> {
            val commands = permissions.map {
                SimpleStepsPage.StepInfo(
                    resources.getString(R.string.adb_command_template_linux_installed, packageName, it),
                    true
                )
            }
            return arrayOf(
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_rhel_1)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_rhel_2)
                ),
                SimpleStepsPage.StepInfo(
                    getString(R.string.adb_install_adb_general_end_1)
                )
            ) + commands +
                    arrayOf(
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_2)
                        ),
                        SimpleStepsPage.StepInfo(
                            getString(R.string.adb_install_adb_general_end_3)
                        )
                    )
        }
    }
    object GenericLinux : SelectedOS(R.string.adb_linux_other, R.drawable.penguin) {
        override fun Context.makeSteps(permissions: Array<String>): Array<SimpleStepsPage.StepInfo> {
            val commands = permissions.map {
                SimpleStepsPage.StepInfo(
                    resources.getString(R.string.adb_command_template_mac_linux, packageName, it),
                    true