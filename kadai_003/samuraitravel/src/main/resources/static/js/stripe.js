const stripe = Stripe('pk_test_51SeQthEBuX6P4fxnNDGXuLaU52x7RgUxnsdZhNhm5tXQRFtf3Bm4QpKJWDj5XoRtGndd8Y8myzVenoOkWFv74Jh700oKkbkKva');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
    stripe.redirectToCheckout({
        sessionId: sessionId
    })
});