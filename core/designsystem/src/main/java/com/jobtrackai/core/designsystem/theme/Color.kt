package com.jobtrackai.core.designsystem.theme

import androidx.compose.ui.graphics.Color

/**
 * Raw brand color values. Nothing in the rest of the app should reference
 * these directly — always go through [ColorScheme][androidx.compose.material3.ColorScheme]
 * via `MaterialTheme.colorScheme` inside [com.jobtrackai.core.designsystem.theme.JobTrackTheme].
 * That indirection is what makes dark mode (Section 45) and any future
 * rebrand a one-file change instead of a find-and-replace across every
 * screen (Rule 45: "do not hard-code colors throughout the UI").
 *
 * Palette choice: a deep indigo/blue primary (trust, professionalism —
 * appropriate for a tool recruiters and hiring managers might glance at
 * over a candidate's shoulder, per Section 28's "recruiter-friendly"
 * brief) with a warm amber accent reserved for things that need genuine
 * attention (interview reminders, offers).
 */
object JobTrackColors {

    // --- Primary: indigo ---
    val Primary10 = Color(0xFF00105C)
    val Primary20 = Color(0xFF001C88)
    val Primary30 = Color(0xFF0029B3)
    val Primary40 = Color(0xFF3547D6)
    val Primary80 = Color(0xFFBAC3FF)
    val Primary90 = Color(0xFFDEE0FF)

    // --- Secondary: slate ---
    val Secondary10 = Color(0xFF141B2C)
    val Secondary20 = Color(0xFF283350)
    val Secondary30 = Color(0xFF3F4B6B)
    val Secondary40 = Color(0xFF576487)
    val Secondary80 = Color(0xFFC0C8EC)
    val Secondary90 = Color(0xFFDCE1FF)

    // --- Tertiary: amber (offers, reminders, positive highlights) ---
    val Tertiary10 = Color(0xFF2B1700)
    val Tertiary20 = Color(0xFF492A00)
    val Tertiary30 = Color(0xFF6B3F00)
    val Tertiary40 = Color(0xFF8F5500)
    val Tertiary80 = Color(0xFFFFBA7B)
    val Tertiary90 = Color(0xFFFFDCB8)

    // --- Error ---
    val Error10 = Color(0xFF410002)
    val Error20 = Color(0xFF690005)
    val Error30 = Color(0xFF93000A)
    val Error40 = Color(0xFFBA1A1A)
    val Error80 = Color(0xFFFFB4AB)
    val Error90 = Color(0xFFFFDAD6)

    // --- Neutral surfaces ---
    val Neutral10 = Color(0xFF1B1B1F)
    val Neutral20 = Color(0xFF303034)
    val Neutral90 = Color(0xFFE4E1E6)
    val Neutral95 = Color(0xFFF2EFF4)
    val Neutral99 = Color(0xFFFDFBFF)

    val NeutralVariant30 = Color(0xFF454650)
    val NeutralVariant50 = Color(0xFF767680)
    val NeutralVariant80 = Color(0xFFC6C5D0)
    val NeutralVariant90 = Color(0xFFE2E1EC)

    // --- Application-stage accents (Section 9 Kanban) ---
    // Not part of the Material color scheme — these are semantic, fixed
    // colors that stay constant across light/dark mode (a "TECHNICAL_
    // INTERVIEW" chip is always teal), similar to how GitHub's PR-state
    // colors don't flip with the OS theme. `feature:applications` (Phase 9)
    // maps its `ApplicationStage` enum onto these once that enum exists —
    // deliberately not wired to an enum here, since inventing that mapping
    // ahead of the type it maps would be guessing at an API that doesn't
    // exist yet.
    val StageSaved = Color(0xFF757780)
    val StageApplied = Color(0xFF3547D6)
    val StageScreening = Color(0xFF6B3F00)
    val StageAssessment = Color(0xFF8F5500)
    val StageTechnicalInterview = Color(0xFF006A6A)
    val StageHrInterview = Color(0xFF6750A4)
    val StageFinalInterview = Color(0xFF4A6572)
    val StageOffer = Color(0xFF2E7D32)
    val StageRejected = Color(0xFFBA1A1A)
    val StageWithdrawn = Color(0xFF757780)
}
