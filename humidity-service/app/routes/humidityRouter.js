const express = require("express");
const router = express.Router();

const humidityController = require("../controllers/humidityController");

router.post("/add", async (req, res) => {
    try {
        const temp = req.body.data.temp;
        const time = req.body.data.time;
        var result = await humidityController.storeHumidity(temp, time);
        result = {"type": req.body.type, "data": req.body.data};
        res.json(result);
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

module.exports = router;