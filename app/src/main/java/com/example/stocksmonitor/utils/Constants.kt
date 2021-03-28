package com.example.stocksmonitor.utils

object Constants {

    const val MBOUM_URL = "https://mboum.com/api/v1/"
    const val MBOUM_API_KEY = "Aln23OOuedgyQVrvZY6bBdlat9BFfzbnnwvCiS4Utd8kC7OvWSlFXXjHaiHt"
    const val FINNHUB_URL = "https://finnhub.io/api/v1/"
    const val FINNHUB_API_KEY = "c0ln4cn48v6orbr1itrg"

    //   Тикеры индекса S&P 500
    val S_AND_P_TICKERS = listOf(
        "AAPL", "MSFT", "AMZN", "FB", "JPM", "BRK.B", "JNJ", "GOOG", "GOOGL", "XOM", "BAC", "WFC",
        "INTC", "T", "V", "CSCO", "CVX", "UNH", "PFE", "HD", "PG", "VZ", "C", "ABBV", "BA", "KO",
        "CMCSA", "MA", "PM", "DWDP", "PEP", "ORCL", "DIS", "MRK", "NVDA", "MMM", "AMGN", "IBM",
        "NFLX", "WMT", "MO", "MCD", "GE", "HON", "MDT", "ABT", "TXN", "BMY", "ADBE", "UNP", "GILD",
        "BKNG", "AVGO", "ACN", "UTX", "GS", "SLB", "CAT", "PYPL", "QCOM", "CRM", "NKE", "TMO", "USB",
        "SBUX", "LMT", "COST", "MS", "PNC", "LLY", "UPS", "TWX", "NEE", "CELG", "LOW", "BLK", "CVS",
        "AXP", "MU", "CHTR", "SCHW", "MDLZ", "CB", "COP", "AMAT", "DHR", "AMT", "CL", "GD", "FDX",
        "RTN", "WBA", "NOC", "BIIB", "BDX", "ANTM", "AET", "EOG", "BK", "ATVI", "CME", "AGN", "MON",
        "SYK", "DUK", "ITW", "ADP", "TJX", "DE", "CSX", "SPGI", "AIG", "MET", "CTSH", "OXY", "ISRG",
        "SPG", "GM", "COF", "PRU", "D", "PX", "EMR", "CCI", "ICE", "VRTX", "SO", "BBT", "ESRX", "MMC",
        "MAR", "INTU", "F", "EBAY", "ZTS", "NSC", "VLO", "CI", "PSX", "HAL", "KHC", "KMB", "STT",
        "FOXA", "HPQ", "STZ", "EA", "HUM", "BSX", "TGT", "TRV", "DAL", "APD", "TEL", "ILMN", "AON",
        "LRCX", "JCI", "EXC", "ETN", "ECL", "AFL", "LYB", "WM", "PGR", "ADI", "ALL", "STI", "SHW",
        "BAX", "MPC", "PLD", "FIS", "MCK", "LUV", "EL", "APC", "AEP", "EQIX", "KMI", "FISV", "WDC",
        "HPE", "DXC", "ADSK", "PPG", "ROST", "EW", "GIS", "PSA", "HCA", "SYY", "MTB", "PXD", "ROP",
        "APH", "TROW", "DFS", "MCO", "YUM", "RHT", "SRE", "CCL", "SYF", "ALXN", "WY", "REGN", "GLW",
        "CMI", "ADM", "FCX", "APTV", "MNST", "AAL", "VFC", "FITB", "PH", "PEG", "ROK", "FTV", "SWK",
        "AMP", "OKE", "ZBH", "PCAR", "ED", "KEY", "DG", "NTRS", "WMB", "PCG", "MCHP", "RF", "A",
        "CAH", "MYL", "IP", "CFG", "TSN", "IR", "DLTR", "CXO", "AVB", "XEL", "COL", "WLTW", "EQR",
        "DLR", "RCL", "DPS", "KR", "NUE", "ORLY", "PAYX", "SWKS", "SBAC", "NEM", "EIX", "WELL", "CERN",
        "HIG", "ALGN", "BXP", "WEC", "XLNX", "HRS", "CBS", "KLAC", "PPL", "UAL", "GPN", "MGM", "BBY",
        "AME", "DTE", "VTR", "IDXX", "AZO", "INFO", "ES", "MSI", "LH", "NTAP", "CNC", "HBAN", "CMA",
        "STX", "K", "OMC", "DVN", "WRK", "CTL", "LNC", "SYMC", "WAT", "CLX", "ABC", "PFG", "INCY",
        "HLT", "FAST", "LEN", "VRSK", "FOX", "LLL", "EMN", "VMC", "MTD", "RSG", "ETFC", "CAG", "TXT",
        "MHK", "URI", "DOV", "ESS", "IQV", "DHI", "CTAS", "TSS", "SJM", "TPR", "XL", "WYNN", "TAP",
        "EFX", "NBL", "FE", "BLL", "O", "DGX", "GWW", "AWK", "TDG", "HSY", "ANSS", "CBG", "ANDV", "NOV",
        "L", "XYL", "IVZ", "RMD", "BF.B", "HST", "NWL", "ETR", "CTXS", "CBOE", "GPC", "MAS", "EXPE",
        "GGP", "APA", "XRAY", "MLM", "FTI", "SNPS", "MKC", "AEE", "CHD", "BEN", "EQT", "CHRW", "BHGE",
        "HES", "AJG", "AKAM", "RJF", "MRO", "WYN", "COG", "CMS", "ARE", "ULTA", "WHR", "LKQ", "HII",
        "DRI", "VAR", "VNO", "KMX", "KSU", "EXPD", "PNR", "CNP", "VIAB", "PVH", "UNM", "PRGO", "COO",
        "IT", "ALB", "CA", "KSS", "PKG", "ZION", "CINF", "NCLH", "ADS", "CDNS", "NLSN", "IFF", "AMG",
        "FMC", "UHS", "DVA", "HOLX", "EXR", "HSIC", "VRSN", "RE", "JBHT", "HCP", "QRVO", "ARNC",
        "LB", "TIF", "AVY", "HAS", "BWA", "MAA", "SLG", "JNPR", "NDAQ", "IPGP", "AOS", "MOS", "TMK",
        "FFIV", "WU", "FBHS", "KORS", "AMD", "UDR", "CF", "IPG", "DISH", "NRG", "M", "DRE", "COTY",
        "HRL", "LNT", "IRM", "SNA", "XEC", "REG", "AAP", "FRT", "CPB", "ALK", "PNW", "PKI", "TSCO",
        "PHM", "CMG", "ALLE", "SEE", "LUK", "FLR", "HOG", "NI", "RHI", "HBI", "GPS", "JEC", "GT",
        "DISCK", "GRMN", "FLIR", "AES", "PBCT", "HP", "XRX", "CSRA", "AYI", "MAC", "LEG", "AIV", "NWSA",
        "RL", "KIM", "JWN", "FLS", "HRB", "SCG", "FL", "SRCL", "PWR", "BHF", "EVHC", "AIZ", "MAT",
        "TRIP", "NFX", "DISCA", "NAVI", "RRC", "UAA", "SIG", "CHK", "UAA", "PDCO", "NWS"
    )
    const val IMAGES_URL = "https://finnhub.io/api/logo?symbol="
    const val DB_NAME = "stocks_monitor_database"
    const val STOCKS_MONITOR_PREFERENCES = "STOCKS_MONITOR_PREFERENCES"
    const val USD_CURRENCY = "USD"
    const val MAX_TAGS_COUNT = 20
    const val REQUEST_SEARCH = 701
    const val RESULT_UPDATE = 501
}