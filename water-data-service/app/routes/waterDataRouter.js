const express = require("express");
const router = express.Router();

const waterDataController = require("../controllers/waterDataController");

// router
//     .route("/")
//     .get(waterDataController.getAllWaterData);

// router
//     .route("/add")
//     .post(waterDataController.addWaterData);

// router
//     .route("/:id")
//     .get(waterDataController.getWaterData);

router.get("/", async (req, res) => {
    try {
        const db = req.app.locals.db;
        const result = await waterDataController.getAllWaterData(db);
        res.json(result);
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

router.get("/:id", async (req, res) => {
    try {
        const db = req.app.locals.db;
        const id = req.params.id;
        const result = await waterDataController.getWaterData(db, id);
        res.json(result);
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

router.delete("/:id", async (req, res) => {
    try {
        const db = req.app.locals.db;
        const id = req.params.id;
        const result = await waterDataController.deleteWaterData(db, id);
        res.json(result);
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});

router.post("/add", async (req, res) => {
    try {
        const db = req.app.locals.db;
        const temp = req.body.temp;
        const result = await waterDataController.addWaterData(db, temp);
        res.json(result);
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});



module.exports = router;