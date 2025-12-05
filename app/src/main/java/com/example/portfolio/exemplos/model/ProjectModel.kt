package com.example.portfolio.exemplos.model

import androidx.compose.runtime.Immutable
import com.example.portfolio.exemplos.Dest

data class ProjectModel(val name: String, val description: String, val dest: Dest)

//@Immutable
//data class UiProjectState(
//    val items: List<ProjectModel>
//)