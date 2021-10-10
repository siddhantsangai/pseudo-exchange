****************************************************************************************************
Tech Stack : 

-Backend: SpringBoot

-Database : The Data is not persisted.

****************************************************************************************************

Features Implemented:

-A matching engine which matches buy/sell orders. The orders are matched using the FIFO principle.

-All orders are considered limit orders. Orders which are partially filled will stay open. 

-Ability to register user and generate unique token

-API to place buy/sell orders.

-Stock Symbol data is read from a JSON and associated order books are created.

-Generation of a portfolio per token id.

-The portfolio stores open orders and stock holding for a particular user(token id).

****************************************************************************************************

API Docs:

To register the user and generate token

	URL: /register
	Method: GET

Success Response:

	Code: 200 
	Content:
	{
		"tokenId": "ddpygkqcopiufia"
	}
	
	
****************************************************************************************************
To place buy orders

	URL: /buy
	Method: PUT

Required:

    {
	    "tokenId": "zaqrgauziwenpmc",
	    "symbol" : "INFY",
	    "side" : "B",
	    "quantity" : "30",
	    "price" : "120"
    }

Success Response:

	Code: 200
	Content: 
	{
		"orderId": "nryits"
	}
	
Failure Response:

	Code: 400
	Content:
	{
		"error": "Error message"
	}
	
	
*****************************************************************************************************	
To place sell orders

	URl: /sell
	Method: PUT
	
Required:

	{
	    "tokenId": "ddpygkqcopiufia",
	    "symbol" : "ABB",
	    "side" : "S",
	    "quantity" : "30",
	    "price" : "110"
	}
	
Success Response:

	Code: 200
	Content:
	{
		"orderId": "mstjry"
	}
	
Failure Response:

	Code: 400
	Content:
	{
		"error": "Error message"
	}



****************************************************************************************************
To retrieve the user portfolio i.e stock holdings and a list of open orders.

	URL: /portfolio
	Method: GET
	
Required: 

	{
	    "tokenId" : "kuewsapojnukxuz"
	}
	
Success Response:

	{
	    "tokenId": "zaqrgauziwenpmc",
	    "orders": {
		"kfxurx": {
		    "tokenId": "zaqrgauziwenpmc",
		    "orderId": "kfxurx",
		    "symbol": "ABB",
		    "side": "B",
		    "quantity": 10,
		    "price": 120.0,
		    "timeStamp": 1633866507681
		},
		"nryits": {
		    "tokenId": "zaqrgauziwenpmc",
		    "orderId": "nryits",
		    "symbol": "INFY",
		    "side": "B",
		    "quantity": 30,
		    "price": 120.0,
		    "timeStamp": 1633866531897
		}
	    },
	    "stockHoldings": {
		"ABB": 30
	    }
	}
	
Failure Response:

	Code: 400
	Content:
	{
		"error": "Error message"
	}
