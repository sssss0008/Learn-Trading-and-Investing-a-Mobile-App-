package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Course
import com.example.model.Flashcard
import com.example.model.GlossaryTerm
import com.example.model.Lesson
import com.example.model.SkillTreeNode
import com.example.ui.components.EducationalDisclaimerCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldBullish
import com.example.ui.theme.FinancialIconType
import com.example.ui.theme.FinancialVectorIcon
import com.example.ui.theme.IndigoBlue
import com.example.ui.theme.LocalFinancialColors
import com.example.ui.theme.RoseBearish

@Composable
fun LearnScreen(
    modifier: Modifier = Modifier,
    courses: List<Course>,
    flashcards: List<Flashcard>,
    glossary: List<GlossaryTerm>,
    skillTreeNodes: List<SkillTreeNode>,
    selectedCourseId: String? = null,
    onSelectCourse: (String?) -> Unit = {}
) {
    val finColors = LocalFinancialColors.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Courses", "Roadmap", "Flashcards", "Glossary", "Skill Tree")

    // Active lesson view state inside selected course
    var activeLesson by remember { mutableStateOf<Lesson?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (selectedCourseId == null && activeLesson == null) {
            // Main Top Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = CyanAccent,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = CyanAccent,
                        height = 2.5.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                color = if (selectedTab == index) CyanAccent else finColors.textMuted
                            )
                        }
                    )
                }
            }

            when (selectedTab) {
                0 -> CourseLibraryTab(courses = courses, onSelectCourse = onSelectCourse)
                1 -> LearningRoadmapTab(onSelectCourse = onSelectCourse)
                2 -> FlashcardsTab(flashcards = flashcards)
                3 -> GlossaryTab(glossary = glossary)
                4 -> SkillTreeTab(skillTreeNodes = skillTreeNodes)
            }
        } else if (activeLesson != null) {
            // Lesson Reader Screen
            LessonReaderScreen(
                lesson = activeLesson!!,
                onBack = { activeLesson = null }
            )
        } else {
            // Course Detail Screen
            val course = courses.find { it.id == selectedCourseId } ?: courses.first()
            CourseDetailScreen(
                course = course,
                onBack = { onSelectCourse(null) },
                onSelectLesson = { lesson -> activeLesson = lesson }
            )
        }
    }
}

@Composable
private fun CourseLibraryTab(
    courses: List<Course>,
    onSelectCourse: (String) -> Unit
) {
    val finColors = LocalFinancialColors.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Academy Curriculum",
                subtitle = "${courses.size} structured educational tracks"
            )
        }

        items(courses) { course ->
            CourseCardItem(course = course, onClick = { onSelectCourse(course.id) })
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun CourseCardItem(
    course: Course,
    onClick: () -> Unit
) {
    val finColors = LocalFinancialColors.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .border(1.dp, finColors.cardBorder, RoundedCornerShape(16.dp))
            .testTag("course_card_${course.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = CyanAccent.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = course.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = "${course.estimatedMinutes} min • ${course.level}",
                    style = MaterialTheme.typography.labelSmall,
                    color = finColors.textMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = course.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = course.description,
                style = MaterialTheme.typography.bodySmall,
                color = finColors.textSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = { course.progressPercent / 100f },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = if (course.progressPercent == 100) EmeraldBullish else CyanAccent,
                    trackColor = finColors.cardBorder
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "${course.progressPercent}%",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (course.progressPercent == 100) EmeraldBullish else CyanAccent
                )
            }
        }
    }
}

@Composable
private fun CourseDetailScreen(
    course: Course,
    onBack: () -> Unit,
    onSelectLesson: (Lesson) -> Unit
) {
    val finColors = LocalFinancialColors.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = CyanAccent)
                }
                Text(
                    text = "Back to Courses",
                    style = MaterialTheme.typography.titleSmall,
                    color = CyanAccent,
                    modifier = Modifier.clickable { onBack() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = course.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = course.description,
                style = MaterialTheme.typography.bodyMedium,
                color = finColors.textSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            SectionHeader(title = "Course Modules & Lessons")
        }

        course.modules.forEach { module ->
            item {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = finColors.cardBorder.copy(alpha = 0.35f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = module.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = CyanAccent
                        )
                        Text(
                            text = module.description,
                            style = MaterialTheme.typography.labelSmall,
                            color = finColors.textMuted
                        )
                    }
                }
            }

            items(module.lessons) { lesson ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectLesson(lesson) }
                        .border(1.dp, finColors.cardBorder, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(
                                    if (lesson.isCompleted) EmeraldBullish.copy(alpha = 0.2f) else finColors.cardBorder,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (lesson.isCompleted) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldBullish, modifier = Modifier.size(18.dp))
                            } else {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = finColors.textMuted, modifier = Modifier.size(14.dp))
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = lesson.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${lesson.readingTimeMin} min read • ${lesson.keyConcepts.joinToString(", ")}",
                                style = MaterialTheme.typography.labelSmall,
                                color = finColors.textSecondary
                            )
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Open Lesson",
                            tint = CyanAccent,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun LessonReaderScreen(
    lesson: Lesson,
    onBack: () -> Unit
) {
    val finColors = LocalFinancialColors.current
    var selectedQuizOption by remember { mutableIntStateOf(-1) }
    var quizSubmitted by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = CyanAccent)
                }
                Text(
                    text = "Back to Module",
                    style = MaterialTheme.typography.titleSmall,
                    color = CyanAccent,
                    modifier = Modifier.clickable { onBack() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = lesson.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = lesson.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = finColors.textSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Lesson Sections
        items(lesson.contentSections) { section ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = section.heading,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = section.body,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 22.sp
                    )

                    if (section.formula != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = IndigoBlue.copy(alpha = 0.12f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, IndigoBlue.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "FORMULA:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = IndigoBlue
                                )
                                Text(
                                    text = section.formula,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }

                    if (section.example != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AmberGold.copy(alpha = 0.10f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Example: ${section.example}",
                                style = MaterialTheme.typography.bodySmall,
                                color = AmberGold,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    if (section.keyTakeaway != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "💡 Key Takeaway: ${section.keyTakeaway}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = EmeraldBullish
                        )
                    }
                }
            }
        }

        // Quiz at the end
        if (lesson.quiz.isNotEmpty()) {
            val q = lesson.quiz.first()
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionHeader(title = "Lesson Mastery Check")

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = q.question,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        q.options.forEachIndexed { i, opt ->
                            val isSelected = selectedQuizOption == i
                            val isCorrect = i == q.correctIndex

                            val bg = when {
                                quizSubmitted && isCorrect -> EmeraldBullish.copy(alpha = 0.2f)
                                quizSubmitted && isSelected && !isCorrect -> RoseBearish.copy(alpha = 0.2f)
                                isSelected -> CyanAccent.copy(alpha = 0.15f)
                                else -> finColors.cardBorder.copy(alpha = 0.3f)
                            }

                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable(enabled = !quizSubmitted) { selectedQuizOption = i },
                                color = bg,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${('A' + i)}. $opt",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (!quizSubmitted) {
                            Button(
                                onClick = { quizSubmitted = true },
                                enabled = selectedQuizOption != -1,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
                            ) {
                                Text("Check Answer", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = finColors.cardBorder.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = if (selectedQuizOption == q.correctIndex) "✓ Correct!" else "Explanation:",
                                        fontWeight = FontWeight.Bold,
                                        color = if (selectedQuizOption == q.correctIndex) EmeraldBullish else AmberGold
                                    )
                                    Text(text = q.explanation, style = MaterialTheme.typography.bodySmall, color = finColors.textSecondary)
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun LearningRoadmapTab(onSelectCourse: (String) -> Unit) {
    val finColors = LocalFinancialColors.current

    val roadmapSteps = listOf(
        Pair("1. FINANCIAL FOUNDATIONS", "Money mechanics, inflation, saving vs investing, compound interest"),
        Pair("2. FINANCIAL MARKETS ECOSYSTEM", "Stocks, bonds, forex, commodities, crypto, derivatives"),
        Pair("3. STOCK MARKET & OWNERSHIP", "Shares, market cap, IPOs, dividends, EPS, equity fundamentals"),
        Pair("4. TECHNICAL ANALYSIS ACADEMY", "Price action, Japanese candlesticks, trends, S&R, indicators"),
        Pair("5. FUNDAMENTAL ANALYSIS ACADEMY", "Income statement, balance sheet, cash flows, valuation, DCF"),
        Pair("6. RISK MANAGEMENT PROTOCOLS", "Position sizing, stop-losses, drawdowns, market psychology"),
        Pair("7. PORTFOLIO ARCHITECTURE", "Asset allocation, correlation, index funds, rebalancing strategies"),
        Pair("8. ADVANCED STRATEGIES & RESEARCH", "Backtesting, institutional thesis, equity research reports")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Financial Mastery Roadmap",
                subtitle = "Progressive learner sequence from foundations to portfolio mastery"
            )
        }

        items(roadmapSteps.size) { i ->
            val step = roadmapSteps[i]
            val isUnlocked = i <= 4

            Row(modifier = Modifier.fillMaxWidth()) {
                // Timeline spine
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                if (isUnlocked) CyanAccent.copy(alpha = 0.2f) else finColors.cardBorder,
                                CircleShape
                            )
                            .border(1.5.dp, if (isUnlocked) CyanAccent else finColors.textMuted, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${i + 1}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) CyanAccent else finColors.textMuted
                        )
                    }

                    if (i < roadmapSteps.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(50.dp)
                                .background(if (isUnlocked) CyanAccent.copy(alpha = 0.5f) else finColors.cardBorder)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectCourse("technical_analysis") }
                        .border(1.dp, finColors.cardBorder, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = step.first,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) CyanAccent else finColors.textMuted
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = step.second,
                            style = MaterialTheme.typography.bodySmall,
                            color = finColors.textSecondary
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun FlashcardsTab(flashcards: List<Flashcard>) {
    val finColors = LocalFinancialColors.current
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    val currentCard = flashcards.getOrNull(currentIndex) ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionHeader(
            title = "Spaced Repetition Flashcards",
            subtitle = "Card ${currentIndex + 1} of ${flashcards.size}"
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Big Flashcard Card with Flip animation
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable { isFlipped = !isFlipped }
                .border(1.5.dp, if (isFlipped) CyanAccent else finColors.cardBorder, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = AmberGold.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = currentCard.category.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = AmberGold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (!isFlipped) {
                        Text(
                            text = currentCard.term,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Flip, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Tap to reveal explanation", style = MaterialTheme.typography.labelSmall, color = CyanAccent)
                        }
                    } else {
                        Text(
                            text = currentCard.definition,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            lineHeight = 22.sp
                        )
                        if (currentCard.keyFormula != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Formula: ${currentCard.keyFormula}",
                                style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                                color = CyanAccent,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "e.g. ${currentCard.example}",
                            style = MaterialTheme.typography.labelSmall,
                            color = finColors.textMuted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Next / Prev controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = {
                    if (currentIndex > 0) {
                        currentIndex--
                        isFlipped = false
                    }
                },
                enabled = currentIndex > 0,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Previous")
            }

            Button(
                onClick = {
                    if (currentIndex < flashcards.size - 1) {
                        currentIndex++
                        isFlipped = false
                    }
                },
                enabled = currentIndex < flashcards.size - 1,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CyanAccent, contentColor = Color.Black)
            ) {
                Text("Next Term", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun GlossaryTab(glossary: List<GlossaryTerm>) {
    val finColors = LocalFinancialColors.current
    var searchQuery by remember { mutableStateOf("") }

    val filtered = remember(searchQuery, glossary) {
        if (searchQuery.isBlank()) glossary
        else glossary.filter {
            it.term.contains(searchQuery, ignoreCase = true) ||
            it.category.contains(searchQuery, ignoreCase = true) ||
            it.simpleDefinition.contains(searchQuery, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_glossary"),
                placeholder = { Text("Search financial dictionary...") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = finColors.textMuted) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyanAccent,
                    unfocusedBorderColor = finColors.cardBorder
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Financial Dictionary",
                subtitle = "${filtered.size} definitions available"
            )
        }

        items(filtered) { term ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(1.dp, finColors.cardBorder, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = term.term,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CyanAccent
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = finColors.cardBorder.copy(alpha = 0.5f)
                        ) {
                            Text(
                                text = term.category,
                                style = MaterialTheme.typography.labelSmall,
                                color = finColors.textMuted,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = term.simpleDefinition,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Technical context: ${term.technicalExplanation}",
                        style = MaterialTheme.typography.labelSmall,
                        color = finColors.textSecondary
                    )

                    if (term.formula != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Formula: ${term.formula}",
                            style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                            fontWeight = FontWeight.SemiBold,
                            color = AmberGold
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun SkillTreeTab(skillTreeNodes: List<SkillTreeNode>) {
    val finColors = LocalFinancialColors.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            SectionHeader(
                title = "Learner Skill Tree",
                subtitle = "Unlock specialized capabilities through active practice"
            )
        }

        items(skillTreeNodes) { node ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .border(
                        1.dp,
                        if (node.isUnlocked) CyanAccent.copy(alpha = 0.5f) else finColors.cardBorder,
                        RoundedCornerShape(14.dp)
                    ),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = finColors.cardBackground)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                if (node.isUnlocked) CyanAccent.copy(alpha = 0.15f) else finColors.cardBorder,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (node.isUnlocked) Icons.Default.CheckCircle else Icons.Default.Star,
                            contentDescription = null,
                            tint = if (node.isUnlocked) CyanAccent else finColors.textMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = node.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Level: ${node.levelName}",
                            style = MaterialTheme.typography.labelSmall,
                            color = finColors.textMuted
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { node.progress / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(2.5.dp)),
                            color = if (node.progress == 100) EmeraldBullish else CyanAccent,
                            trackColor = finColors.cardBorder
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "${node.progress}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (node.progress == 100) EmeraldBullish else CyanAccent
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
