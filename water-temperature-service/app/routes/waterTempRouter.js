const express = require("express");
const router = express.Router();

const waterTempController = require("../controllers/waterTempController");

router.post("/add", async (req, res) => {
    try {
        const value = req.body.temp
        const time = req.body.time
        var result = await waterTempController.storeWaterTemp(value, time)
        result = {"type": "water-temperature", "data": result}
        res.json(result)
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

module.exports = router;