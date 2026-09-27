package com.example.model

data class Course(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val level: String, // Beginner, Intermediate, Advanced
    val estimatedMinutes: Int,
    val lessonsCount: Int,
    val progressPercent: Int = 0,
    val iconType: String = "STOCKS",
    val modules: List<CourseModule> = emptyList()
)

data class CourseModule(
    val id: String,
    val title: String,
    val description: String,
    val lessons: List<Lesson> = emptyList()
)

data class Lesson(
    val id: String,
    val courseId: String,
    val title: String,
    val summary: String,
    val readingTimeMin: Int,
    val keyConcepts: List<String>,
    val contentSections: List<LessonSection>,
    val quiz: List<QuizQuestion> = emptyList(),
    val isCompleted: Boolean = false
)

data class LessonSection(
    val heading: String,
    val body: String,
    val keyTakeaway: String? = null,
    val formula: String? = null,
    val example: String? = null
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class Flashcard(
    val id: String,
    val category: String,
    val term: String,
    val definition: String,
    val example: String,
    val keyFormula: String? = null,
    var isMastered: Boolean = false
)

data class DailyChallenge(
    val id: String,
    val title: String,
    val question: String,
    val scenario: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val xpReward: Int = 50,
    val completed: Boolean = false
)

data class CertificateInfo(
    val id: String,
    val title: String,
    val category: String,
    val issueDate: String,
    val recipientName: String = "Awiskar Acharya",
    val isUnlocked: Boolean = false,
    val requiredProgress: Int = 100
)

data class SkillTreeNode(
    val id: String,
    val title: String,
    val levelName: String,
    val isUnlocked: Boolean,
    val progress: Int,
    val parentId: String? = null
)

data class GlossaryTerm(
    val term: String,
    val category: String,
    val simpleDefinition: String,
    val technicalExplanation: String,
    val example: String,
    val formula: String? = null,
    val relatedTerms: List<String> = emptyList()
)
