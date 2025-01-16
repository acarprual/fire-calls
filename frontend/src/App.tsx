import { useEffect } from "react";

import { FireCallsFormFactory } from "./sections/fire_calls/FireCallsFormFactory";

export function App() {
	useEffect(() => {
		document.title = "Fire calls";
	}, []);

	return (
		<div className="fire-calls">
			<h2 className="fire-calls-title">Current fire calls</h2>
			<FireCallsFormFactory />
		</div>
	);
}
