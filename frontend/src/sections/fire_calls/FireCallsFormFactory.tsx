import { FireCallRepository } from "../../infrastructure/FireCallRepository";
import { FireCallsForm } from "./FireCallsForm";

const fireCallRepository = new FireCallRepository();
export function FireCallsFormFactory() {
	return <FireCallsForm fireCallRepository={fireCallRepository} />;
}
