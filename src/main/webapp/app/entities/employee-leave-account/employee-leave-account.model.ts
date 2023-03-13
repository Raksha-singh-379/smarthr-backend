import dayjs from 'dayjs/esm';
import { ILeaveType } from 'app/entities/leave-type/leave-type.model';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface IEmployeeLeaveAccount {
  id: number;
  carriedLeaves?: number | null;
  creditedLeaves?: number | null;
  date?: dayjs.Dayjs | null;
  balance?: number | null;
  companyId?: number | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  leaveType?: Pick<ILeaveType, 'id' | 'leaveType'> | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewEmployeeLeaveAccount = Omit<IEmployeeLeaveAccount, 'id'> & { id: null };
