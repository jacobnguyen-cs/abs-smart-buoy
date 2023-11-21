const express = require("express");
const router = express.Router();

const airTempController = require("../controllers/airTempController");

router.post("/add", async (req, res) => {
    try {
        const payload = req.body.RawPayload;
        const temp = payload.data.temp;
        const time = payload.data.time;
        var result = await airTempController.storeAirTemp(temp, time);
        result = {"type": payload.type, "data": payload.data};
        res.json(result)
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

module.exports = router;