import sqlite3

conn = sqlite3.connect('example.sqlite')

cursor = conn.cursor()

cursor.execute("""--sql
    CREATE TABLE IF NOT EXISTS users (
        id INTEGER PRIMARY KEY,
        username TEXT NOT NULL,
        email TEXT NOT NULL
    );
""")

cursor.execute("""--sql
    INSERT INTO users (username, email)
    VALUES ('mc', 'mc.big@mcd.com')
""")

conn.commit()
conn.close()