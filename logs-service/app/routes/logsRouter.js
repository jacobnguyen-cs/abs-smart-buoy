const express = require("express");
const router = express.Router();

const logsController = require("../controllers/logsController");

router.post("/add", async (req, res) => {
    try {
        const log = req.body.data.log;
        const time = req.body.data.time;
        var result = await logsController.storeLog(log, time);
        result = {"type": req.body.type, "data": req.body.data};
        res.json(result);
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

module.exports = router;