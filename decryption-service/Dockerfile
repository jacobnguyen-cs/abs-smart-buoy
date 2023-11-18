FROM python:3-alpine

COPY ./requirements.txt /app/requirements.txt
WORKDIR /app
RUN pip install -r requirements.txt

COPY ./app /app
COPY run.py /app/runy.py

EXPOSE 5000

ENTRYPOINT ["python"]
CMD ["run.py"]