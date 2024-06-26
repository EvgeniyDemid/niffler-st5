import {useMutation} from "@apollo/client";
import {useState} from "react";
import {UPDATE_SPEND_MUTATION} from "../../api/graphql/mutations";
import {showError, showSuccess} from "../../toaster/toaster";
import {ButtonIcon, IconType} from "../ButtonIcon";
import {Checkbox} from "../Checkbox";
import {EditableSelect} from "../EditableSelect";
import {EditableValue} from "../EditableValue";

export const SpendingRow = ({
                                spending,
                                isSelected,
                                handleCheckboxClick,
                                isGraphOutdated,
                                setIsGraphOutdated,
                                categories
                            }) => {

    const [updateSpending] = useMutation(UPDATE_SPEND_MUTATION);
    const [editableSpending, setEditableSpending] = useState(spending);
    const [isEdit, setIsEdit] = useState(false);

    const handleDataSave = (evt) => {
        const data = editableSpending;
        delete data["__typename"];
        evt.preventDefault();
        updateSpending({
            variables: {
                generateSpend: {
                    ...data,
                    amount: parseFloat(data.amount),
                }
            }
        }).then(res => {
            if (res?.data?.updateSpend !== null) {
                showSuccess("Spending successfully updated");
                setIsGraphOutdated(!isGraphOutdated);
            } else {
                showError("Spending was not updated");
                console.log(err);
            }
        }).catch((err) => {
            showError("Can not update Spending row");
            console.log(err);
        });
    };

    return (
        <tr>
            <td><Checkbox id={spending?.id} handleSingleClick={handleCheckboxClick} selected={isSelected}/></td>
            <td>
                <EditableValue
                    type="date"
                    value={editableSpending?.spendDate}
                    isEditState={isEdit}
                    fieldName={"spendDate"}
                    placeholder={null}
                    onValueChange={(spendDate) => setEditableSpending({...editableSpending, spendDate})}
                />
            </td>
            <td>
                <EditableValue
                    type="text"
                    value={editableSpending?.amount}
                    isEditState={isEdit}
                    fieldName={"amount"}
                    placeholder={null}
                    onValueChange={(evt) => setEditableSpending({...editableSpending, amount: evt.target.value})}
                />
            </td>
            <td>{spending?.currency}</td>
            <td>
                <EditableSelect
                    value={editableSpending?.category}
                    options={categories}
                    isEditState={isEdit}
                    onValueChange={(category) => setEditableSpending({...editableSpending, category: category.value})}
                />
            </td>
            <td>
                <EditableValue
                    type="text"
                    value={editableSpending?.description}
                    isEditState={isEdit}
                    fieldName={"description"}
                    placeholder={null}
                    onValueChange={(evt) => setEditableSpending({...editableSpending, description: evt.target.value})}
                />
            </td>
            <td>
                {isEdit ?
                    (
                        <div className={"spendings__button-group"}>
                            <ButtonIcon iconType={IconType.SUBMIT} onClick={(evt) => {
                                handleDataSave(evt);
                                setIsEdit(false);
                            }
                            }/>
                            <ButtonIcon iconType={IconType.CLOSE} onClick={() => {
                                setEditableSpending(spending);
                                setIsEdit(false)
                            }}/>
                        </div>
                    )
                    :
                    (<ButtonIcon iconType={IconType.EDIT} onClick={() => setIsEdit(true)}/>)}
            </td>
        </tr>

    );

};
