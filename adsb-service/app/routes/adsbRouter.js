const express = require("express");
const router = express.Router();

const adsbController = require("../controllers/adsbController");

router.post("/add", async (req, res) => {
    try {
        console.log(req.body.data)
        const time = req.body.data.time;
        const addr = req.body.data.addr;
        const lat = req.body.data.lat;
        const long = req.body.data.long
        var result = await adsbController.storeADSBData(lat, addr, long, time)
        result = {"type": req.body.type, "data": req.body.data};
        res.json(result);
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

module.exports = router;