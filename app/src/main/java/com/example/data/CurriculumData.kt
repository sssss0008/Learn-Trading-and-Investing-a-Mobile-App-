package com.example.data

import com.example.model.CertificateInfo
import com.example.model.Course
import com.example.model.CourseModule
import com.example.model.DailyChallenge
import com.example.model.Flashcard
import com.example.model.GlossaryTerm
import com.example.model.Lesson
import com.example.model.LessonSection
import com.example.model.QuizQuestion
import com.example.model.SkillTreeNode

object CurriculumData {

    val courses: List<Course> = listOf(
        Course(
            id = "foundations",
            title = "Financial Foundations & Money Mechanics",
            category = "Foundations",
            description = "Master core financial literacy: money velocity, saving vs investing, inflation, assets vs liabilities, and the miracle of compound interest.",
            level = "Beginner",
            estimatedMinutes = 45,
            lessonsCount = 4,
            progressPercent = 100,
            iconType = "COMPOUND_GROWTH",
            modules = listOf(
                CourseModule(
                    id = "mod_foundations_1",
                    title = "The Architecture of Wealth",
                    description = "Understanding economic energy and capital formation.",
                    lessons = listOf(
                        Lesson(
                            id = "foundations_1",
                            courseId = "foundations",
                            title = "Money, Inflation & Purchasing Power",
                            summary = "Explore why cash sitting still loses value and how inflation silently erodes your real wealth.",
                            readingTimeMin = 8,
                            keyConcepts = listOf("Fiat Currency", "Purchasing Power", "CPI Inflation", "Real Return"),
                            contentSections = listOf(
                                LessonSection(
                                    heading = "The Time Value of Money",
                                    body = "Money is not just paper or bank digits; it is stored human time and economic utility. Inflation is the gradual decline in the purchasing power of money, reflected in a general increase in prices.",
                                    keyTakeaway = "A dollar today is worth more than a dollar tomorrow because of its earning potential and the erosion caused by inflation.",
                                    formula = "Real Return = Nominal Return - Inflation Rate",
                                    example = "If a savings account yields 2% but inflation is 3.5%, your real return is -1.5% annually."
                                ),
                                LessonSection(
                                    heading = "Assets vs. Liabilities",
                                    body = "In pure financial terms: an asset puts money into your pocket (dividends, interest, capital appreciation), while a liability extracts cash out of your pocket (debt interest, recurring depreciation).",
                                    keyTakeaway = "Wealth accumulation is the deliberate process of transforming active income into cash-flowing or appreciating assets."
                                )
                            ),
                            quiz = listOf(
                                QuizQuestion(
                                    id = "q_found_1",
                                    question = "If an investment returns 7% annually while inflation runs at 3%, what is the approximate real return?",
                                    options = listOf("10%", "4%", "2.3%", "7%"),
                                    correctIndex = 1,
                                    explanation = "Real return is calculated by subtracting inflation from the nominal return (7% - 3% = 4%)."
                                )
                            ),
                            isCompleted = true
                        ),
                        Lesson(
                            id = "foundations_2",
                            courseId = "foundations",
                            title = "Compound Interest: The 8th Wonder",
                            summary = "The exponential mathematical law that transforms regular, disciplined investing into generational wealth.",
                            readingTimeMin = 10,
                            keyConcepts = listOf("Exponential Growth", "Rule of 72", "Contribution Frequency", "Time Horizon"),
                            contentSections = listOf(
                                LessonSection(
                                    heading = "How Compounding Works",
                                    body = "Compound interest occurs when you earn interest not only on the initial principal but also on the accumulated interest from prior periods. Early in the compounding journey, progress seems slow. Over decades, compounding curves sharply upward.",
                                    formula = "A = P * (1 + r/n)^(nt)",
                                    keyTakeaway = "Time in the market is vastly more powerful than timing the market due to the exponential nature of compounding."
                                ),
                                LessonSection(
                                    heading = "The Rule of 72",
                                    body = "A fast mental shortcut to determine how many years it will take for your investment to double at a constant annual rate of return.",
                                    formula = "Years to Double ≈ 72 / Annual Interest Rate",
                                    example = "At an 8% annual return, an investment doubles in approximately 72 / 8 = 9 years."
                                )
                            ),
                            quiz = listOf(
                                QuizQuestion(
                                    id = "q_found_2",
                                    question = "Using the Rule of 72, how many years will it take an investment returning 9% annually to double?",
                                    options = listOf("6 years", "8 years", "9 years", "12 years"),
                                    correctIndex = 1,
                                    explanation = "72 / 9 = 8 years."
                                )
                            ),
                            isCompleted = true
                        )
                    )
                )
            )
        ),
        Course(
            id = "technical_analysis",
            title = "Technical Analysis & Chart Mastery",
            category = "Trading & Analysis",
            description = "Decode price action, candlesticks, support/resistance, moving averages, RSI, MACD, and market structure.",
            level = "Intermediate",
            estimatedMinutes = 65,
            lessonsCount = 6,
            progressPercent = 68,
            iconType = "CANDLESTICK",
            modules = listOf(
                CourseModule(
                    id = "mod_ta_1",
                    title = "Price Action & Japanese Candlesticks",
                    description = "Reading the visual struggle between buyers (bulls) and sellers (bears).",
                    lessons = listOf(
                        Lesson(
                            id = "ta_1",
                            courseId = "technical_analysis",
                            title = "Candlestick Anatomy & Key Reversals",
                            summary = "Understanding Open, High, Low, Close (OHLC), wick rejection, hammers, engulfing patterns, and dojis.",
                            readingTimeMin = 12,
                            keyConcepts = listOf("OHLC", "Wicks vs Bodies", "Bullish Hammer", "Bearish Engulfing", "Doji"),
                            contentSections = listOf(
                                LessonSection(
                                    heading = "The Four Coordinates of Price",
                                    body = "Each candle encapsulates the complete battle within a chosen timeframe: Open (starting price), High (bull peak), Low (bear bottom), and Close (settlement). Long wicks reveal rejection: sellers pushed price down, or buyers vigorously defended support.",
                                    keyTakeaway = "Candlesticks reveal emotion, liquidity, and momentum in real-time."
                                ),
                                LessonSection(
                                    heading = "The Hammer & Bullish Engulfing",
                                    body = "A Hammer features a small upper body and a long lower wick at least twice the height of the body, indicating buyers rejected lower levels. A Bullish Engulfing candle completely swallows the prior red candle's body.",
                                    example = "A hammer at key daily support often suggests that bears exhausted their supply."
                                )
                            ),
                            quiz = listOf(
                                QuizQuestion(
                                    id = "q_ta_1",
                                    question = "What does a long lower wick on a candlestick indicate?",
                                    options = listOf(
                                        "Strong selling pressure at market close",
                                        "Intraday price drop followed by strong buying rejection",
                                        "Market indecision with low volume",
                                        "Guaranteed breakout higher next day"
                                    ),
                                    correctIndex = 1,
                                    explanation = "A long lower wick proves that bears pushed prices down, but aggressive buyers stepped in before the close."
                                )
                            ),
                            isCompleted = true
                        ),
                        Lesson(
                            id = "ta_2",
                            courseId = "technical_analysis",
                            title = "Support, Resistance & Market Structure",
                            summary = "How order books, historical memory, and psychological rounds create floors and ceilings.",
                            readingTimeMin = 14,
                            keyConcepts = listOf("Support Floor", "Resistance Ceiling", "Breakout & Retest", "Higher Highs/Higher Lows"),
                            contentSections = listOf(
                                LessonSection(
                                    heading = "Principle of Polarity",
                                    body = "When broken decisively with volume, prior resistance transitions into future support, and broken support transitions into resistance.",
                                    keyTakeaway = "Always trade with structural confirmation rather than anticipating breaks without proof."
                                )
                            ),
                            quiz = listOf(
                                QuizQuestion(
                                    id = "q_ta_2",
                                    question = "What happens according to the Principle of Polarity when major resistance is breached?",
                                    options = listOf(
                                        "The level ceases to exist permanently",
                                        "The broken resistance often acts as new support",
                                        "The asset will immediately crash back down",
                                        "Volume always decreases to zero"
                                    ),
                                    correctIndex = 1,
                                    explanation = "Previous resistance becomes future support as traders who missed the breakout look to buy pullbacks."
                                )
                            ),
                            isCompleted = true
                        )
                    )
                )
            )
        ),
        Course(
            id = "fundamental_analysis",
            title = "Fundamental Analysis & Valuation",
            category = "Investing & Research",
            description = "Read financial statements like an institutional analyst: Income Statement, Balance Sheet, Cash Flow, and DCF modeling.",
            level = "Advanced",
            estimatedMinutes = 80,
            lessonsCount = 5,
            progressPercent = 40,
            iconType = "BALANCE_SHEET",
            modules = listOf(
                CourseModule(
                    id = "mod_fa_1",
                    title = "Deconstructing Financial Statements",
                    description = "Understanding GAAP/IFRS disclosures and corporate profitability.",
                    lessons = listOf(
                        Lesson(
                            id = "fa_1",
                            courseId = "fundamental_analysis",
                            title = "The Three Master Statements",
                            summary = "How the Income Statement, Balance Sheet, and Cash Flow Statement link together to reveal business reality.",
                            readingTimeMin = 15,
                            keyConcepts = listOf("Revenue vs Profit", "Operating Cash Flow", "Working Capital", "Net Margin"),
                            contentSections = listOf(
                                LessonSection(
                                    heading = "Cash vs. Accrual Accounting",
                                    body = "Revenue on the Income Statement does not mean cash in the bank. The Cash Flow statement reveals the actual liquidity generated by operations.",
                                    keyTakeaway = "Net Income is an opinion; Free Cash Flow is a fact."
                                )
                            ),
                            quiz = listOf(
                                QuizQuestion(
                                    id = "q_fa_1",
                                    question = "Which statement reveals actual cash generated by core operations after Capex?",
                                    options = listOf(
                                        "Balance Sheet",
                                        "Free Cash Flow (Cash Flow Statement)",
                                        "Statement of Retained Earnings",
                                        "Income Statement Gross Margin"
                                    ),
                                    correctIndex = 1,
                                    explanation = "Free Cash Flow = Operating Cash Flow - Capital Expenditures (Capex)."
                                )
                            )
                        )
                    )
                )
            )
        ),
        Course(
            id = "risk_management",
            title = "Risk Management & Trading Psychology",
            category = "Trading & Risk",
            description = "Protect your capital: position sizing, risk/reward ratios, maximum drawdowns, and conquering FOMO and fear.",
            level = "Intermediate",
            estimatedMinutes = 50,
            lessonsCount = 4,
            progressPercent = 50,
            iconType = "RISK_SHIELD",
            modules = listOf(
                CourseModule(
                    id = "mod_rm_1",
                    title = "Mathematical Capital Preservation",
                    description = "Survival is the prerequisite for outperformance.",
                    lessons = listOf(
                        Lesson(
                            id = "rm_1",
                            courseId = "risk_management",
                            title = "The 1% Risk Rule & Position Sizing",
                            summary = "Why professional traders never risk more than 1-2% of their total equity on any single idea.",
                            readingTimeMin = 12,
                            keyConcepts = listOf("Risk Amount", "Stop Loss Distance", "Position Sizing", "Asymmetric R:R"),
                            contentSections = listOf(
                                LessonSection(
                                    heading = "Never Confuse Position Size with Risk Size",
                                    body = "Buying $10,000 worth of stock with a stop-loss $500 below entry is a $500 risk, not a $10,000 risk.",
                                    formula = "Position Size (Shares) = (Account Size * Risk %) / (Entry Price - Stop Price)",
                                    example = "On a $50,000 account, a 1% risk is $500. If entry is $100 and stop is $95 (risk $5/share), you buy 100 shares ($10,000 total position)."
                                )
                            ),
                            quiz = listOf(
                                QuizQuestion(
                                    id = "q_rm_1",
                                    question = "On a $100,000 account with a 1% risk limit ($1,000), if you buy at $50 with a stop at $48 ($2 risk/share), how many shares should you purchase?",
                                    options = listOf("2,000 shares", "500 shares", "1,000 shares", "250 shares"),
                                    correctIndex = 1,
                                    explanation = "$1,000 total risk / $2 risk per share = 500 shares."
                                )
                            )
                        )
                    )
                )
            )
        ),
        Course(
            id = "etf_indexing",
            title = "ETFs, Indexing & Portfolio Architecture",
            category = "Investing & Portfolio",
            description = "Build resilient, all-weather portfolios using low-cost index funds, factor ETFs, and strategic asset allocation.",
            level = "Beginner",
            estimatedMinutes = 40,
            lessonsCount = 3,
            progressPercent = 25,
            iconType = "ETF"
        ),
        Course(
            id = "fixed_income",
            title = "Bonds, Yields & Macroeconomics",
            category = "Fixed Income & Macro",
            description = "Master government and corporate bonds, coupon rates, yield curve inversions, and central bank monetary policy.",
            level = "Advanced",
            estimatedMinutes = 55,
            lessonsCount = 4,
            progressPercent = 10,
            iconType = "BONDS"
        ),
        Course(
            id = "forex_commodities",
            title = "Forex, Currencies & Commodity Markets",
            category = "Global Markets",
            description = "Understand global currency pairs, exchange rates, interest rate differentials, gold, oil, and agricultural futures.",
            level = "Intermediate",
            estimatedMinutes = 45,
            lessonsCount = 3,
            progressPercent = 0,
            iconType = "FOREX"
        ),
        Course(
            id = "crypto_blockchain",
            title = "Cryptocurrency & Blockchain Fundamentals",
            category = "Digital Assets",
            description = "Explore decentralized ledgers, Bitcoin monetary policy, Ethereum smart contracts, security, and volatility risk.",
            level = "Beginner",
            estimatedMinutes = 40,
            lessonsCount = 3,
            progressPercent = 0,
            iconType = "CRYPTO"
        )
    )

    val dailyChallenges: List<DailyChallenge> = listOf(
        DailyChallenge(
            id = "dc_1",
            title = "Identify the Trend Structure",
            scenario = "Asset XYZ has printed three consecutive swing highs at $120, $128, and $135, with intervening lows at $112, $119, and $126.",
            question = "Which structural market regime does this pattern represent?",
            options = listOf(
                "Downtrend (Lower Lows & Lower Highs)",
                "Uptrend (Higher Highs & Higher Lows)",
                "Sideways Consolidation / Rangebound",
                "Distribution Phase"
            ),
            correctIndex = 1,
            explanation = "An uptrend is strictly defined by ascending swing highs and ascending swing lows.",
            xpReward = 50
        ),
        DailyChallenge(
            id = "dc_2",
            title = "P/E Valuation Calculation",
            scenario = "A company trades at a share price of $150. Over the past 12 months, its diluted EPS was $6.00.",
            question = "What is the company's Trailing Twelve Months (TTM) P/E ratio?",
            options = listOf("25.0x", "18.5x", "30.0x", "9.0x"),
            correctIndex = 0,
            explanation = "P/E Ratio = Price ($150) / EPS ($6.00) = 25.0x."
        )
    )

    val flashcards: List<Flashcard> = listOf(
        Flashcard(
            id = "fc_1",
            category = "Valuation",
            term = "P/E Ratio (Price-to-Earnings)",
            definition = "A valuation metric comparing a company's current stock price to its per-share earnings. Measures how much investors pay per dollar of profit.",
            example = "A P/E of 20 means investors pay $20 for every $1 of annual earnings.",
            keyFormula = "P/E = Market Price per Share / Earnings per Share (EPS)"
        ),
        Flashcard(
            id = "fc_2",
            category = "Technical Analysis",
            term = "RSI (Relative Strength Index)",
            definition = "A momentum oscillator measuring the speed and change of price movements on a scale from 0 to 100.",
            example = "Readings above 70 typically signal overbought conditions; readings below 30 signal oversold conditions.",
            keyFormula = "RSI = 100 - [100 / (1 + RS)], RS = Avg Gain / Avg Loss"
        ),
        Flashcard(
            id = "fc_3",
            category = "Risk Management",
            term = "Maximum Drawdown (MDD)",
            definition = "The maximum peak-to-trough decline in a portfolio or asset value before a new peak is attained.",
            example = "If a $100k account falls to $70k before recovering to $110k, the maximum drawdown was 30%.",
            keyFormula = "MDD = (Trough Value - Peak Value) / Peak Value"
        ),
        Flashcard(
            id = "fc_4",
            category = "Fixed Income",
            term = "Yield to Maturity (YTM)",
            definition = "The total expected annual rate of return on a bond if held until it matures, factoring in coupon payments and price discount/premium.",
            example = "A bond trading below par ($950 with $1000 par and 5% coupon) offers a YTM greater than its 5% coupon rate."
        ),
        Flashcard(
            id = "fc_5",
            category = "Portfolio",
            term = "Beta (β)",
            definition = "A measure of an asset's volatility in relation to the overall market (usually S&P 500, with a beta of 1.0).",
            example = "A stock with Beta 1.5 is theoretically 50% more volatile than the benchmark index."
        )
    )

    val certificates: List<CertificateInfo> = listOf(
        CertificateInfo(
            id = "cert_foundations",
            title = "Financial Foundations Certified",
            category = "Core Financial Literacy",
            issueDate = "September 2026",
            isUnlocked = true
        ),
        CertificateInfo(
            id = "cert_ta",
            title = "Technical Analysis Practitioner",
            category = "Price Action & Charting",
            issueDate = "In Progress",
            isUnlocked = false,
            requiredProgress = 100
        ),
        CertificateInfo(
            id = "cert_fa",
            title = "Fundamental Equity Analyst",
            category = "Statements & Valuation",
            issueDate = "Locked",
            isUnlocked = false,
            requiredProgress = 100
        ),
        CertificateInfo(
            id = "cert_risk",
            title = "Risk & Capital Management",
            category = "Portfolio Defense",
            issueDate = "Locked",
            isUnlocked = false,
            requiredProgress = 100
        )
    )

    val glossary: List<GlossaryTerm> = listOf(
        GlossaryTerm(
            term = "Bull Market",
            category = "Market Cycles",
            simpleDefinition = "A sustained period where asset prices are rising and investor sentiment is confident and optimistic.",
            technicalExplanation = "Formally characterized by a 20%+ increase in broad market indices from recent cyclical troughs, sustained across multiple months.",
            example = "The 2009–2020 S&P 500 bull market was the longest in modern history.",
            relatedTerms = listOf("Bear Market", "Higher Highs", "Market Sentiment")
        ),
        GlossaryTerm(
            term = "Bear Market",
            category = "Market Cycles",
            simpleDefinition = "A prolonged market phase characterized by falling prices and widespread pessimism.",
            technicalExplanation = "Conventionally defined as a 20% or greater decline in a major index from recent all-time highs.",
            example = "The 2008 Financial Crisis and the early 2020 pandemic drops represented bear market regimes.",
            relatedTerms = listOf("Bull Market", "Capitulation", "Drawdown")
        ),
        GlossaryTerm(
            term = "CAGR (Compound Annual Growth Rate)",
            category = "Calculations",
            simpleDefinition = "The constant annualized rate at which an investment would have grown if it had compounded smoothly each year.",
            technicalExplanation = "Smooths out the volatility of individual yearly gains and losses over multi-year holding periods.",
            formula = "CAGR = (Ending Value / Beginning Value)^(1 / n) - 1",
            example = "An investment growing from $10,000 to $20,000 over 7.2 years has a CAGR of ~10%."
        ),
        GlossaryTerm(
            term = "DCF (Discounted Cash Flow)",
            category = "Valuation",
            simpleDefinition = "A method of estimating what a business is worth today based on projections of how much cash it will generate in the future.",
            technicalExplanation = "Discounts all future Free Cash Flows back to present value using the company's Weighted Average Cost of Capital (WACC).",
            formula = "PV = FCF_1/(1+r)^1 + FCF_2/(1+r)^2 + ... + Terminal Value/(1+r)^n",
            example = "If a company produces $100M cash next year discounted at 9%, its present value is ~$91.74M."
        ),
        GlossaryTerm(
            term = "Moving Average (SMA vs EMA)",
            category = "Technical Analysis",
            simpleDefinition = "A smoothed line that filters out daily noise by averaging prices over a set number of periods.",
            technicalExplanation = "Simple Moving Average (SMA) weights all periods equally. Exponential Moving Average (EMA) applies greater mathematical weight to recent data.",
            example = "A 50-day EMA reacts faster to a sudden price surge than a 50-day SMA."
        ),
        GlossaryTerm(
            term = "Dividend Yield",
            category = "Income Investing",
            simpleDefinition = "The percentage of a company's share price paid out annually in dividend distributions.",
            technicalExplanation = "Calculated as annual dividend per share divided by the current market share price.",
            formula = "Dividend Yield = Annual Dividend per Share / Stock Price",
            example = "A $100 stock paying $3.50 in annual dividends has a 3.5% dividend yield."
        )
    )

    val skillTreeNodes: List<SkillTreeNode> = listOf(
        SkillTreeNode("sk_1", "Financial Basics", "Foundations", isUnlocked = true, progress = 100),
        SkillTreeNode("sk_2", "Market Mechanics", "Foundations", isUnlocked = true, progress = 100, parentId = "sk_1"),
        SkillTreeNode("sk_3", "Investing Principles", "Core", isUnlocked = true, progress = 75, parentId = "sk_2"),
        SkillTreeNode("sk_4", "Technical Analysis", "Specialization", isUnlocked = true, progress = 68, parentId = "sk_3"),
        SkillTreeNode("sk_5", "Fundamental Analysis", "Specialization", isUnlocked = true, progress = 40, parentId = "sk_3"),
        SkillTreeNode("sk_6", "Risk Management", "Defense", isUnlocked = true, progress = 50, parentId = "sk_4"),
        SkillTreeNode("sk_7", "Portfolio Construction", "Synthesis", isUnlocked = false, progress = 25, parentId = "sk_5"),
        SkillTreeNode("sk_8", "Advanced Market Strategies", "Mastery", isUnlocked = false, progress = 0, parentId = "sk_7")
    )
}
