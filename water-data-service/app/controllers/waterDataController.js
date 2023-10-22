/**
 * Gets all water sensor data
 * @async
 * @function
 * @param {object} db - Database connection
 * @returns {Promise<Array<object>>} A promise that resolves to an array objects containing the water sensor data
 */
async function getAllWaterData (db) {
    const result = await db.runQuery("SELECT * FROM water_data");
    return result;
}

/**
 * Gets the water sensor data specified by the id
 * @async
 * @function
 * @param {object} db - Database connection
 * @param {int} id - Water temperature id
 * @returns {Promise<object>} A promise object containing the water sensor data
 */
async function getWaterData (db, id) {
    const result = await db.runQuery(`SELECT * FROM water_data WHERE id=${id}`);
    return result;
}


/**
 * Adds data to the water sensor
 * @async
 * @function
 * @param {object} db - Database connection
 * @param {decimal} temp - Water temperature in decimal value
 * @returns {Promise<object>} A promise object containing the water sensor data
 */
async function addWaterData (db, temp) {
    const result = await db.runQuery(`INSERT INTO water_data (temp) VALUES(${temp}) RETURNING *`);
    return result;
}

/**
 * Deletes data for the water sensor
 * @async
 * @function
 * @param {object} db - Database connection
 * @param {int} id - Water temperature id
 * @returns {Promise<object>} A promise object containing the water sensor data
 */
async function deleteWaterData (db, id) {
    const result = await db.runQuery(`DELETE FROM water_data WHERE id=${id} RETURNING *`);
    return result;
}

module.exports = {
    getAllWaterData,
    getWaterData,
    addWaterData,
    deleteWaterData
};