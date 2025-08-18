package com.shoppingorderapi.domain.order;

public enum OrderStatus {
	CREATED,          // 주문 생성(결제 전)
	PAYMENT_PENDING,  // 결제 진행중(결제창 이동/대기)
	PAID,             // 결제 완료
	CANCELED,         // 취소(결제 전/후 정책에 맞춰 사용)
	EXPIRED           // 결제 미완료로 자동 만료(타임아웃/세션 종료)
}
