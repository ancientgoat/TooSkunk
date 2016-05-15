"# TooSkunk"

SCrn~1
SCrn
SCr~ref
SCrn~1 collected < 48 hours from SCrn

SCr~hist available
    no
        CKD history?
            if no get SCrest if possible
        get SCr~icu (if available)
        get SCr~admin if possible
    yes
        get SCr~admin if available
        if SCr~hist < SCr~admin
            then    SCr~ref = SCr~admin
            else    SCr~ref = SCr~hist

    raceFactor = 1.212 (if patient is black) else 1
    sexFactor = 0.742 (if patient is female) else 1
    ageFactor = 175 * Math.pow(patient.getAgeInYears(), -0.203)
    gfr = 75
    denominator = (ageFactor * raceFactor * secFactor)

SCr~ref =
s1    new BigDecimal(Math.pow(gfr / denominator, 1 / -1.154)).setScale(2, BigDecimal.ROUND_HALF_UP)


// new CalculatedResult(new BigDecimal(Math.pow(
// 				75 / (175 * Math.pow(patient.getAgeInYears(), -0.203) * 1 * 1), 1 / -1.154))
// 				.setScale(2, BigDecimal.ROUND_HALF_UP), "mg/dL");

SCr~ref = MIN ( SCradmin, SCrest, SCricu )

* SCr~hist = the most recent SCr collected <1 year from the current admission date
* SCr~admin = the lowest SCr collected <24 hours from the current admission date
* SCr~icu = the lowest SCr collected <24 hours of the first admission to the ICU (by transfer date) during the
current encounter
* SCr~est = estimated SCr calculated with the MDRD equation (click HERE for calculation)
