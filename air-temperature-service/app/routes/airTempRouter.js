const express = require("express");
const router = express.Router();

const airTempController = require("../controllers/airTempController");

router.post("/add", async (req, res) => {
    try {
        const value = req.body.temp
        const time = req.body.time
        var result = await airTempController.storeAirTemp(value, time)
        result = {"type": "air-temperature", "data": result}
        res.json(result)
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

module.exports = router;