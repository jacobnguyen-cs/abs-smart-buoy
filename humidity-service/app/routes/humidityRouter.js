const express = require("express");
const router = express.Router();

const humidityController = require("../controllers/humidityController");

router.post("/add", async (req, res) => {
    try {
        const value = req.body.temp
        const time = req.body.time
        var result = await humidityController.storeHumidity(value, time)
        result = {"type": "humidity", "data": result}
        res.json(result)
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

module.exports = router;