const express = require("express");
const router = express.Router();

const waterTempController = require("../controllers/waterTempController");

router.post("/add", async (req, res) => {
    try {
        const temp = req.body.data.temp;
        const time = req.body.data.time;
        var result = await waterTempController.storeWaterTemp(temp, time);
        result = {"type": req.body.type, "data": req.body.data};
        res.json(result);
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

module.exports = router;