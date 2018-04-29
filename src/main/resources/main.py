from flask import Flask, render_template, redirect, request, session

app = Flask(__name__)
app.secret_key = 'You would never guess it!'


@app.route('/')
def route_index():
    return render_template('index.html')

if __name__ == '__main__':
    app.run(
        debug=True  # Allow verbose error reports
    )
