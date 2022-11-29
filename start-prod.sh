#!/usr/bin/bash
cd kafka && docker compose up && cd ..
cd gateway-ms && docker compose --env-file .env.dev up --build && cd ..
cd user-ms && docker compose --env-file .env.dev up --build && cd ..
cd offer-ms && docker compose --env-file .env.dev up --build && cd ..
cd recruitment-ms && docker compose --env-file .env.dev up --build && cd ..
# cd feedback-ms && docker compose --env-file .env.dev up --build && cd ..

