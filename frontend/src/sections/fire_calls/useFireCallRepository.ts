import { useEffect, useState } from "react";

import { Dic } from "../../domain/Dic";
import { FireCallRepository } from "../../infrastructure/FireCallRepository";

export function useFireCallRepository(
	repository: FireCallRepository,
	x: number,
	y: number,
	radius: number,
	type: string
): {
	fireCalls: Dic | undefined;
	isLoading: boolean;
} {
	const [fireCalls, setFireCalls] = useState<Dic>();
	const [isLoading, setIsLoading] = useState(true);

	useEffect(() => {
		setIsLoading(true);
		void repository
			.search(x, y, radius, type)
			.then((fireCalls) => {
				setFireCalls(fireCalls);
				setIsLoading(false);
			})
			.catch((err) => {
				return undefined;
			});
	}, [radius, repository, x, y, type]);

	return { fireCalls, isLoading };
}
