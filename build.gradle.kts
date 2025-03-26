dependencies {
    // Jetpack Compose UI
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.2")

    // ✅ Add Jetpack Navigation Compose (this is important)
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Firebase dependencies (if needed)
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
}
