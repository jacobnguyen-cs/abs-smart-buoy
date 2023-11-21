const express = require("express");
const router = express.Router();

const waterTempController = require("../controllers/waterTempController");

router.post("/add", async (req, res) => {
    try {
        const payload = req.body.RawPayload;
        const temp = payload.data.temp;
        const time = payload.data.time;
        var result = await waterTempController.storeWaterTemp(temp, time);
        result = {"type": payload.type, "data": payload.data};
        res.json(result);
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

module.exports = router;