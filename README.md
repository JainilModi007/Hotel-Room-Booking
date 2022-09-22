# Hotel-Room-Booking
We are breaking the Hotel room booking application into three different microservices, which are as follows: 
API-Gateway - This service is exposed to the outer world and is responsible for routing all requests to the microservices internally. Booking service - This service is responsible for collecting all information related to user booking and sending a confirmation message once the booking is confirmed.
Payment service - This is a dummy payment service; this service is called by the booking service for initiating payment after confirming rooms.
