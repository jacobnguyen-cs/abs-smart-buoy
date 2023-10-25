/**
 * Gets all water sensor data
 * @async
 * @function
 * @param {object} db - Database connection
 * @returns {Promise<Array<object>>} A promise that resolves to an array objects containing the water sensor data
 */
async function getAllWaterTemp (db) {
    const result = await db.runQuery("SELECT * FROM water_temp");
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
async function getWaterTemp (db, id) {
    const result = await db.runQuery(`SELECT * FROM water_temp WHERE id=${id}`);
    return result;
}


/**
 * Adds data to the water sensor
 * @async
 * @function
 * @param {object} db - Database connection
 * @param {int} id - ID for water temperature
 * @param {decimal} temp - Water temperature in decimal value
 * @returns {Promise<object>} A promise object containing the water sensor data
 */
async function addWaterTemp (db, id, temp) {
    const result = await db.runQuery(`INSERT INTO water_temp (id, temp) VALUES(${id}, ${temp}) RETURNING *`);
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
async function deleteWaterTemp (db, id) {
    const result = await db.runQuery(`DELETE FROM water_temp WHERE id=${id} RETURNING *`);
    return result;
}

module.exports = {
    getAllWaterTemp,
    getWaterTemp,
    addWaterTemp,
    deleteWaterTemp
};