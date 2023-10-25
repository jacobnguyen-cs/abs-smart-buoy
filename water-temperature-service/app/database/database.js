const { Pool } = require("pg");
const myConfig = require("./config");


/**
 * A class representing a database connection
 * @class
 * @param {object} [dbConfig=myConfig] - An optional configuration object for the database connection
 */
class Database {
    /**
     * A pool object from the "pg" module representing the connection pool
     * @type {Pool}
     */
    pool;

    /**
     * Creates an instance of Database
     * @constructor
     * @param {object} dbConfig - An optional configuration object for the database connection
     */
    constructor(dbConfig) {
        this.pool = new Pool(dbConfig || myConfig);
    }

    /**
     * Runs a SQL query on the database and returns the resulting rows.
     * @async
     * @function
     * @param {string} query - The SQL query to be executed.
     * @param {array} [params=[]] - An optional array of parameters for the SQL query.
     * @returns {Promise<array>} - A promise that resolves to an array of rows returned by the query.
     */
    async runQuery(query, params = []) {
        const client = await this.pool.connect();
        try {
            const { rows } = await client.query(query, params);
            return rows;
        } catch (err) {
            console.error("Error occured executing query: ", err);
            throw err;
        } finally {
            client.release();
        }
    }

    /**
     * Closes the database connection pool
     * @async
     * @function
     * @returns {Promise<void>} - A promise that resolves when the connection is closed
     */
    async close() {
        await this.pool.end();
    }
}

module.exports = Database;