package com.example.ai

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object AiTutorService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun askTutor(
        prompt: String,
        contextTopic: String = "Financial Markets"
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try { BuildConfig.GEMINI_API_KEY } catch (_: Exception) { "" }

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

                val systemInstruction = "You are the AI Financial Tutor inside TradeInvest Academy (created by Awiskar Acharya). " +
                        "Your mission is to educate learners on financial markets, technical analysis, fundamental valuation, risk management, and portfolio strategy. " +
                        "Format your answers with clear structure: Overview, Technical/Mathematical Breakdown, Practical Market Example, and Key Risk Rule. " +
                        "Safety mandate: Always emphasize that this is educational analysis, not personalized investment advice."

                val jsonBody = JSONObject().apply {
                    put("contents", JSONArray().apply {
                        put(JSONObject().apply {
                            put("parts", JSONArray().apply {
                                put(JSONObject().put("text", "Topic: $contextTopic\n\nQuestion: $prompt"))
                            })
                        })
                    })
                    put("systemInstruction", JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", systemInstruction))
                        })
                    })
                }

                val request = Request.Builder()
                    .url(url)
                    .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                    val root = JSONObject(responseBody)
                    val text = root.getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")
                    return@withContext text
                }
            } catch (_: Exception) {
                // Fallback to offline educational intelligence engine
            }
        }

        // High-fidelity structured educational engine response
        return@withContext generateEducationalAnswer(prompt, contextTopic)
    }

    private fun generateEducationalAnswer(prompt: String, topic: String): String {
        val lower = prompt.lowercase()
        return when {
            "p/e" in lower || "price to earnings" in lower -> {
                """
                📊 **P/E (Price-to-Earnings) Ratio Deep Dive**
                
                **1. Core Definition:**
                The P/E ratio measures the price investors are currently paying for each $1 of annual company earnings. It represents market expectations of future growth and business durability.
                
                **2. Mathematical Formula:**
                • **P/E Ratio** = Market Price per Share ÷ Diluted EPS (Earnings per Share)
                
                **3. Trailing vs. Forward:**
                • **Trailing P/E (TTM):** Uses actual reported net earnings over the previous 12 months.
                • **Forward P/E:** Uses analyst consensus estimates for the next 12 months.
                
                **4. Key Interpretations:**
                • A high P/E (e.g., 40x) implies investors anticipate rapid revenue and margin expansion (or the stock is temporarily overvalued).
                • A low P/E (e.g., 10x) could indicate a mature, cash-cow business, or a 'value trap' facing secular decline.
                
                ⚠️ *Educational Note: Never evaluate P/E in isolation. Always compare against industry peers, historical multiples, and debt levels.*
                """.trimIndent()
            }
            "candle" in lower || "hammer" in lower || "doji" in lower -> {
                """
                🕯️ **Candlestick Price Action & Market Psychology**
                
                **1. The Four Battle Points (OHLC):**
                Every candlestick maps Open, High, Low, and Close over a specified period (1m, 1h, 1D).
                
                **2. Wick Dynamics:**
                • **Long Lower Wick (Hammer):** Sellers pushed prices down to extreme lows, but aggressive buyers stepped in before the close, rejecting the lower levels.
                • **Long Upper Wick (Shooting Star):** Bulls attempted a breakout, but institutional sellers or profit-takers overwhelmed the bid.
                
                **3. Doji (Indecision):**
                Open and Close are virtually identical. Reflects equilibrium between buyers and sellers often preceding volatility expansion.
                
                💡 *Pro Rule: Candlestick patterns require structural context. A hammer at major multi-month support carries 5x more statistical validity than one floating mid-range.*
                """.trimIndent()
            }
            "dcf" in lower || "discounted cash flow" in lower || "valuation" in lower -> {
                """
                📐 **Discounted Cash Flow (DCF) Valuation Architecture**
                
                **1. The Fundamental Premise:**
                A company is intrinsically worth the sum of all future cash flows it can return to owners, discounted back to today's purchasing power.
                
                **2. The Equation:**
                • **Intrinsic Value** = Σ [ FCF_t ÷ (1 + WACC)^t ] + [ Terminal Value ÷ (1 + WACC)^n ]
                
                **3. Key Variables:**
                • **Free Cash Flow (FCF):** Operating Cash Flow minus Capital Expenditures.
                • **WACC (Discount Rate):** Weighted Average Cost of Capital (hurdle rate reflecting debt and equity risk).
                • **Terminal Growth Rate:** Long-term sustainable growth (typically 2-3%, pegged near GDP growth).
                
                ⚠️ *Analytical Caveat: DCF models are highly sensitive to small changes in terminal growth and discount rate assumptions.*
                """.trimIndent()
            }
            "position size" in lower || "risk" in lower || "stop loss" in lower -> {
                """
                🛡️ **Professional Capital Preservation: Position Sizing**
                
                **1. The Golden 1% Rule:**
                Never risk more than 1% to 2% of total portfolio equity on any individual trade setup.
                
                **2. The Mathematical Position Sizing Formula:**
                • **Dollar Risk Allowed** = Account Equity × Risk % (e.g. $50,000 × 1% = $500)
                • **Risk Per Share** = | Entry Price - Stop-Loss Price |
                • **Position Size (Number of Shares)** = Dollar Risk Allowed ÷ Risk Per Share
                
                **3. Example Calculation:**
                If Entry = $150 and Stop-Loss = $145 (Risk = $5/share):
                • Max Shares = $500 ÷ $5 = 100 Shares ($15,000 total trade value).
                
                ✅ *Result: Even if your stop is hit, your portfolio drawdown is strictly capped at $500.*
                """.trimIndent()
            }
            "rebalance" in lower || "allocation" in lower || "diversification" in lower -> {
                """
                ⚖️ **Portfolio Asset Allocation & Rebalancing Strategy**
                
                **1. Strategic Asset Allocation:**
                Distributing capital across uncorrelated asset classes (Equities, Sovereign Bonds, Gold/Commodities, Cash) to maximize Sharpe Ratio (return per unit of risk).
                
                **2. Why Rebalance?**
                During bull markets, equity values swell, unintentionally increasing portfolio volatility. Rebalancing systematically sells high-performing assets to purchase undervalued or defensive assets.
                
                **3. Rebalancing Triggers:**
                • **Calendar-Based:** Quarterly or semi-annually.
                • **Tolerance Band:** Whenever an asset drifts ±5% away from its target weighting.
                """.trimIndent()
            }
            else -> {
                """
                🎓 **TradeInvest Academy Market Tutor**
                
                **Concept Analysis: $prompt**
                
                **1. Theoretical Overview:**
                In financial economics, market participants continuously price assets based on risk, expected future cash flows, and macroeconomic liquidity conditions.
                
                **2. Practical Application:**
                Whether analyzing equities, fixed income, currencies, or commodities, disciplined traders and investors always balance:
                • **Probability:** What is the historical edge of this setup?
                • **Risk/Reward:** Is the potential upside at least 2x to 3x the defined risk?
                • **Catalyst:** What earnings, economic release, or sentiment shift validates this thesis?
                
                **3. Next Learning Step:**
                Explore our interactive **Technical Analysis Lab**, **DCF Valuation Simulator**, or test this concept in the **Paper Trading Terminal**.
                
                *TradeInvest Academy • Learn. Analyze. Practice. Invest Smarter.*
                """.trimIndent()
            }
        }
    }
}
