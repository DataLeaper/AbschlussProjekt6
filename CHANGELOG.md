# 352
- Fix some issues with revertable settings.
- Fix dialog behavior for small displays.
- Fix icon blacklist persistence.
- Update dependencies.

# 351
- Fix showing failure screen for writing to Settings.System.
- Make sure UI states properly revert when settings are reverted.
- Address an ANR when checking for root access.
- Add some info to the UI Sounds option about needing to restart System UI to load the sounds
- Fix intro slider color fade.
- Hide Shizuku tutorial in intro slider below Android 11.
- Add Turkish translation.
- Update translations.

# 350
- Remove a lot of Google dependencies.
- Migrate to Bugsnag from Crashlytics.
- Redo intro sliders in Compose.
- Add a toggle to disable/enable crash reports.
- Fix search icon color.
- Fix disappearing notices in Persistent Options.
- Update some text in the tutorial and intro slides.
- The intro will appear once even if you've already gone through it in this update. Just exit it and it shouldn't show up again.
- Add Mastodon link.
- Use Material Design 3 switches.

# 349
- Update translations.
- Add Italian translation.
- Crash fixes.
- Remove OneUI Tuner references.
- Update icon colors for Android 12+.
- Update dependencies.
- Show keys for predefined Icon Blacklist items.

# 348
- Crash fixes.
- Update translations.
- After editing a custom persistent option, the new value will be immediately written to the device settings.
- Hide OneUI Tuner link on One UI 4 and later.
- Add option to hide lock screen ads on FireOS.
- Add option to allow custom left lock screen shortcut on FireOS.
- Move lock screen settings to specific Lock Screen section.
- Fix lock screen shortcuts resetting on One UI.
- Add "Flashlight", "Do Not Disturb", and "None" options for One UI lock screen shortcuts.

# 347
- Try to prevent reports of OBSERVE_GRANT_REVOKE_PERMISSIONS crashes.
- Make sure AD_ID permission isn't in manifest.
- Address deprecations.

# 346
- Fix temperature slider in Night Mode option.
- Implement a "revert" dialog for dangerous settings.
- UI tweaks.
- Update dependencies.
- Update Immersive Mode description to mention broken Immersive Mode on Android 11 and later.
- Update intro to use Material You theming.
- Request notification permission on Android 13.
- Target API 33.

# 345
- Dangerous preferences show up in red again.
- Add a warning about Night Mode to the terms.
- Implement Material You theming for Android 12 and later.
- Update de